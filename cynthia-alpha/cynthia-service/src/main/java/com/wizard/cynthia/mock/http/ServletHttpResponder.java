package com.wizard.cynthia.mock.http;

import com.github.tomakehurst.wiremock.core.FaultInjector;
import com.github.tomakehurst.wiremock.http.*;
import com.github.tomakehurst.wiremock.servlet.BodyChunker;
import com.github.tomakehurst.wiremock.servlet.FaultInjectorFactory;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.google.common.io.ByteStreams;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ScheduledExecutorService;

import static com.github.tomakehurst.wiremock.common.Exceptions.throwUnchecked;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ServletHttpResponder implements HttpResponder {

    public static final String ORIGINAL_REQUEST_KEY = "wiremock.ORIGINAL_REQUEST";

    private ScheduledExecutorService scheduledExecutorService;

    private FaultInjectorFactory faultHandlerFactory;

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public ServletHttpResponder(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void respond(final Request request, final Response response) {
        if (Thread.currentThread().isInterrupted()) {
            return;
        }

        httpServletRequest.setAttribute(ORIGINAL_REQUEST_KEY, LoggedRequest.createFrom(request));

        if (isAsyncSupported(response, httpServletRequest)) {
            respondAsync(request, response);
        } else {
            respondSync(request, response);
        }
    }

    private void respondSync(Request request, Response response) {
        delayIfRequired(response.getInitialDelay());
        respondTo(request, response);
    }


    private void delayIfRequired(long delayMillis) {
        try {
            MILLISECONDS.sleep(delayMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isAsyncSupported(Response response, HttpServletRequest httpServletRequest) {
        return scheduledExecutorService != null && response.getInitialDelay() > 0 && httpServletRequest.isAsyncSupported();
    }

    private void respondAsync(final Request request, final Response response) {
        final AsyncContext asyncContext = httpServletRequest.startAsync();
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    respondTo(request, response);
                } finally {
                    asyncContext.complete();
                }
            }
        }, response.getInitialDelay(), MILLISECONDS);
    }

    private void respondTo(Request request, Response response) {
        try {
            if (response.wasConfigured()) {
                applyResponse(response, httpServletRequest, httpServletResponse);
            } else /*if (request.getMethod().equals(GET) && shouldForwardToFilesContext) {
                forwardToFilesContext(httpServletRequest, httpServletResponse, request);
            } else */{
                httpServletResponse.sendError(HTTP_NOT_FOUND);
            }
        } catch (Exception e) {
            throwUnchecked(e);
        }
    }

    public void applyResponse(Response response, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Fault fault = response.getFault();
        if (fault != null) {
            FaultInjector faultInjector = buildFaultInjector(httpServletRequest, httpServletResponse);
            fault.apply(faultInjector);
            httpServletResponse.addHeader(Fault.class.getName(), fault.name());
            return;
        }

        if (response.getStatusMessage() == null) {
            httpServletResponse.setStatus(response.getStatus());
        } else {
            httpServletResponse.setStatus(response.getStatus(), response.getStatusMessage());
        }

        for (HttpHeader header: response.getHeaders().all()) {
            for (String value: header.values()) {
                httpServletResponse.addHeader(header.key(), value);
            }
        }

       /* if (chunkedEncodingPolicy == NEVER || (chunkedEncodingPolicy == BODY_FILE && response.hasInlineBody())) {
            httpServletResponse.setContentLength(response.getBody().length);
        }*/

        if (response.shouldAddChunkedDribbleDelay()) {
            writeAndTranslateExceptionsWithChunkedDribbleDelay(httpServletResponse, response.getBodyStream(), response.getChunkedDribbleDelay());
        } else {
            writeAndTranslateExceptions(httpServletResponse, response.getBodyStream());
        }
    }

    private FaultInjector buildFaultInjector(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return faultHandlerFactory.buildFaultInjector(httpServletRequest, httpServletResponse);
    }

    private static void writeAndTranslateExceptions(HttpServletResponse httpServletResponse, InputStream content) {
        try (ServletOutputStream out = httpServletResponse.getOutputStream()) {
            ByteStreams.copy(content, out);
            out.flush();
        } catch (IOException e) {
            throwUnchecked(e);
        } finally {
            try {
                content.close();
            } catch (IOException e) {
                // well, we tried
            }
        }
    }

    private void writeAndTranslateExceptionsWithChunkedDribbleDelay(HttpServletResponse httpServletResponse, InputStream bodyStream, ChunkedDribbleDelay chunkedDribbleDelay) {
        try (ServletOutputStream out = httpServletResponse.getOutputStream()) {
            byte[] body = ByteStreams.toByteArray(bodyStream);

            if (body.length < 1) {
                //notifier.error("Cannot chunk dribble delay when no body set");
                out.flush();
                return;
            }

            byte[][] chunkedBody = BodyChunker.chunkBody(body, chunkedDribbleDelay.getNumberOfChunks());

            int chunkInterval = chunkedDribbleDelay.getTotalDuration() / chunkedBody.length;

            for (byte[] bodyChunk : chunkedBody) {
                Thread.sleep(chunkInterval);
                out.write(bodyChunk);
                out.flush();
            }

        } catch (IOException e) {
            throwUnchecked(e);
        } catch (InterruptedException ignored) {
            // Ignore the interrupt quietly since it's probably the client timing out, which is a completely valid outcome
        }
    }
}