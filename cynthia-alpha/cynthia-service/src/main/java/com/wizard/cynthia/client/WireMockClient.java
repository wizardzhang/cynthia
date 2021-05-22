package com.wizard.cynthia.client;

import com.wizard.cynthia.model.Mapping;
import com.wizard.cynthia.model.MappingPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ZZW
 * @description:
 * @date 2021/4/30 17:05
 */
@FeignClient(name = "com.wizard.cynthia.wiremock-service", url= "localhost:8080", path = "/__admin")
public interface WireMockClient {

    @GetMapping("/mappings")
    MappingPage listMappings();

    @PostMapping("/mappings/new")
    MappingPage createMapping(@RequestBody Mapping mapping);
}
