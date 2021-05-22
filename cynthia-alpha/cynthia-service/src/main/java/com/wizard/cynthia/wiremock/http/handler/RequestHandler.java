package com.wizard.cynthia.wiremock.http.handler;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;

/**
 * @author ZZW
 * @description:
 * @date 2021/5/21 16:46
 */
public interface RequestHandler {
    void handle(HttpRequest request, HttpResponse response);
}
