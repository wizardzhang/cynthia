package com.wizard.cynthia.controller;

import com.wizard.cynthia.api.CommonResponse;
import com.wizard.cynthia.client.WireMockClient;
import com.wizard.cynthia.model.Mapping;
import com.wizard.cynthia.model.MappingPage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author ZZW
 * @Date 2021/4/28 16:33
 */
@RestController
@Log4j2
public class MocoController {

    @Autowired
    private WireMockClient wireMockClient;


    @GetMapping("/mappings")
    public CommonResponse<MappingPage> listMappings() {
        return CommonResponse.success(wireMockClient.listMappings());
    }

    @PostMapping("/mappings")
    public  CommonResponse<Object> createMapping(@RequestBody Mapping mapping) {
        wireMockClient.createMapping(mapping);
        return CommonResponse.success(null);
    }
}
