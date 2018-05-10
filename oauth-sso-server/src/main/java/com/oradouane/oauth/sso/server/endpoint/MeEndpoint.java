package com.oradouane.oauth.sso.server.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MeEndpoint {

    @GetMapping("/sso/me")
    public Principal user(final Principal principal) {
        return principal;
    }

    @GetMapping("/sso/helloadmin")
    public String helloAdmin(final Principal principal) {
        return "Hello ADMIN " + principal.getName() + " \n";
    }

    @GetMapping("/sso/hellouser")
    public String helloUser(final Principal principal) {
        return "Hello USER " + principal.getName() + " \n";
    }
}
