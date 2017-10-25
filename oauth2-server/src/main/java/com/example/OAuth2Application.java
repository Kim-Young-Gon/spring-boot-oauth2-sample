package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAuthorizationServer
@SpringBootApplication
public class OAuth2Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2Application.class, args);
    }
}
