package com.oradouane.sample.app.two.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SampleTwoController {

    @GetMapping("/sample2")
    public String sample(Principal principal) {
        return "Hi " + principal.getName() + " from app TWO";
    }
}
