package com.wizard.cynthia.wiremock.servlet;

import com.google.common.io.ByteStreams;
import com.wizard.cynthia.wiremock.http.handler.RequestHandler;
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
import java.nio.charset.StandardCharsets;

/**
 * @author ZZW
 * @description:
 * @date 2021/5/21 16:21
 */
@WebServlet(urlPatterns = {"/mock/*"})
@Log4j2
public class WireMockHandlerDispatchingServlet extends HttpServlet {

    private RequestHandler requestHandler;


    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
       /* requestHandler = (RequestHandler) servletContext.getAttribute("StubRequestHandler");*/
        log.info("servlet init");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("servlet service");
        resp.setStatus(HttpServletResponse.SC_OK);
        InputStream content = new ByteArrayInputStream("hello world".getBytes());
        writeAndTranslateExceptions(resp, content);
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
