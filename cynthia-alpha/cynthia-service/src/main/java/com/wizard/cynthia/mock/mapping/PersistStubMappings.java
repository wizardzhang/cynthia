package com.wizard.cynthia.mock.mapping;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.github.tomakehurst.wiremock.stubbing.StubMappings;
import com.google.common.base.Optional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author ZZW
 * @description: 持久化的mapping
 * @date 2021/5/28 11:23
 */
@Service
public class PersistStubMappings implements StubMappings {

    @Override
    public ServeEvent serveFor(Request request) {
        return null;
    }

    @Override
    public void addMapping(StubMapping mapping) {

    }

    @Override
    public void removeMapping(StubMapping mapping) {

    }

    @Override
    public void editMapping(StubMapping stubMapping) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void resetScenarios() {

    }

    @Override
    public List<StubMapping> getAll() {
        return null;
    }

    @Override
    public Optional<StubMapping> get(UUID id) {
        return null;
    }

    @Override
    public List<Scenario> getAllScenarios() {
        return null;
    }

    @Override
    public List<StubMapping> findByMetadata(StringValuePattern pattern) {
        return null;
    }
}
