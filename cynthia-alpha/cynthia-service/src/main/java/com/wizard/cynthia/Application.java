package com.wizard.cynthia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description:
 * @Author ZZW
 * @Date 2021/4/28 15:40
 */
@SpringBootApplication
@ServletComponentScan
@EnableFeignClients(basePackages = {"com.wizard.cynthia.client"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
