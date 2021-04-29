package com.wizard.cynthia.controller;

import com.github.dreamhead.moco.*;
import com.wizard.cynthia.model.JsonConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.github.dreamhead.moco.Moco.*;
import static com.github.dreamhead.moco.Moco.json;
import static com.github.dreamhead.moco.MocoRequestHit.requestHit;
import static com.github.dreamhead.moco.MocoRest.get;
import static com.github.dreamhead.moco.MocoRest.restServer;
import static com.github.dreamhead.moco.Runner.runner;
import static com.github.dreamhead.moco.Runner.running;

/**
 * @Description:
 * @Author ZZW
 * @Date 2021/4/28 16:33
 */
@RestController
@Log4j2
public class MocoController {
    @PostMapping("/mock-server")
    public void createMockServer(@RequestBody JsonConfig jsonConfig) throws Exception {

        Plain resource1 = new Plain();
        resource1.code = 1;
        resource1.message = "hello";

        Plain resource2 = new Plain();
        resource2.code = 2;
        resource2.message = "world";

        final RequestHit hit = requestHit();
        final RestServer server = restServer(12306, hit, log());
        server.resource("targets",
                get("1").response(json(resource1)),
                get("2").response(json(resource2))
        );

        running(server, () -> {

        });
    }

    private static class Plain {
        public int code;
        public String message;
    }
}
