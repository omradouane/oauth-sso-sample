package com.oradouane.sample.app.two.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SampleController {

    @GetMapping("/sample")
    public String sample(Principal principal) {
        return "Hi " + principal.getName() + " from app ONE";
    }
}
