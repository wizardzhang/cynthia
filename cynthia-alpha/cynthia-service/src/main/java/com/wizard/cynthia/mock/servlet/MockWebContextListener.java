package com.wizard.cynthia.mock.servlet;

import com.github.tomakehurst.wiremock.core.WireMockApp;
import com.github.tomakehurst.wiremock.http.StubRequestHandler;
import com.github.tomakehurst.wiremock.servlet.NotImplementedContainer;
import com.github.tomakehurst.wiremock.standalone.CommandLineOptions;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author ZZW
 * @description:
 * @date 2021/5/20 15:56
 */
@WebListener
public class MockWebContextListener implements ServletContextListener {
    private static final String APP_CONTEXT_KEY = "WireMockApp";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        CommandLineOptions options = new CommandLineOptions();
        WireMockApp wireMockApp = new WireMockApp(options, new NotImplementedContainer());
        context.setAttribute(APP_CONTEXT_KEY, wireMockApp);
        context.setAttribute(StubRequestHandler.class.getName(), wireMockApp.buildStubRequestHandler());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
