package com.dazuizui.eureka2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka注册中心
 * create time: 2020-6-2
 */
@EnableEurekaServer
@SpringBootApplication
public class Eureka2Application {
    public static void main(String[] args) {
        SpringApplication.run(Eureka2Application.class, args);
    }
}
