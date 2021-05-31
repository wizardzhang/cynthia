package com.wizard.cynthia.mock.servlet;

import com.github.tomakehurst.wiremock.http.HttpResponder;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestHandler;
import com.github.tomakehurst.wiremock.servlet.MultipartRequestConfigurer;
import com.github.tomakehurst.wiremock.servlet.WireMockHandlerDispatchingServlet;
import com.github.tomakehurst.wiremock.servlet.WireMockHttpServletRequestAdapter;
import com.google.common.io.ByteStreams;
import com.wizard.cynthia.mock.http.ServletHttpResponder;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ZZW
 * @description:
 * @date 2021/5/21 16:21
 */
@WebServlet(urlPatterns = {"/mock/*"})
@Log4j2
public class MockHandlerDispatchingServlet extends HttpServlet {

    private RequestHandler requestHandler;

    private MultipartRequestConfigurer multipartRequestConfigurer;

    private String mappedUnder;


    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        requestHandler = (RequestHandler) servletContext.getAttribute("com.github.tomakehurst.wiremock.http.StubRequestHandler");
        log.info("servlet init");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Request request = new WireMockHttpServletRequestAdapter(req, multipartRequestConfigurer, mappedUnder);
        ServletHttpResponder responder = new ServletHttpResponder(req, resp);
        requestHandler.handle(request, responder);
    }

    private static void writeAndTranslateExceptions(HttpServletResponse httpServletResponse, InputStream content) {
        try (ServletOutputStream out = httpServletResponse.getOutputStream()) {
            ByteStreams.copy(content, out);
            out.flush();
        } catch (IOException e) {
            log.error("error", e);
        } finally {
            try {
                content.close();
            } catch (IOException e) {
                // well, we tried
            }
        }
    }
}
