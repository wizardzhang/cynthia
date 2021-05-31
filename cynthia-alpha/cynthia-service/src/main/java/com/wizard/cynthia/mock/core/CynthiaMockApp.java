package com.wizard.cynthia.mock.core;

import com.github.tomakehurst.wiremock.core.StubServer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.wizard.cynthia.mock.mapping.PersistStubMappings;
import org.springframework.stereotype.Service;

/**
 * @author ZZW
 * @description:
 * @date 2021/5/28 11:33
 */
@Service
//TODO: 仿照WireMockApp来重写
public class CynthiaMockApp implements StubServer {

    private final PersistStubMappings persistStubMappings;


    public CynthiaMockApp(PersistStubMappings persistStubMappings) {
        this.persistStubMappings = persistStubMappings;
    }

    @Override
    public ServeEvent serveStubFor(Request request) {
        return persistStubMappings.serveFor(request);
    }
}
