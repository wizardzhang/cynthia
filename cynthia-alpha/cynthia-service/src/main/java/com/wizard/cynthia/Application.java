package com.wizard.cynthia;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * @Description:
 * @Author ZZW
 * @Date 2021/4/28 15:40
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.wizard.cynthia.client"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
       /* WireMockServer wireMockServer = new WireMockServer(options().port(8089));
        wireMockServer.start();*/
    }
}
