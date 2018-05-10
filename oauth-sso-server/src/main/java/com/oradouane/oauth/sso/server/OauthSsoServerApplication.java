package com.oradouane.oauth.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
//@EnableResourceServer
public class OauthSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthSsoServerApplication.class, args);
    }

    // no security requirement
    @RequestMapping("/")
    public String home() {
        return "OAuth SSO Server is Running\n";
    }

}

