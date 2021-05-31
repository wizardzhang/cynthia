package com.wizard.cynthia.mock.http;

import com.github.tomakehurst.wiremock.extension.requestfilter.RequestFilter;
import com.github.tomakehurst.wiremock.http.*;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

import java.util.List;

/**
 * @author ZZW
 * @description:
 * @date 2021/5/21 16:48
 */
public class CynthiaStubRequestHandler extends AbstractRequestHandler {
    public CynthiaStubRequestHandler(ResponseRenderer responseRenderer, List<RequestFilter> requestFilters) {
        super(responseRenderer, requestFilters);
    }

    @Override
    public void handle(Request request, HttpResponder httpResponder) {

    }

    @Override
    protected ServeEvent handleRequest(Request request) {
        return null;
    }
}
