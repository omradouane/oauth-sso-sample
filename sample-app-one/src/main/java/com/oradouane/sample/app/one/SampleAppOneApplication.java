package com.oradouane.sample.app.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@EnableResourceServer
@SpringBootApplication
public class SampleAppOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleAppOneApplication.class, args);
    }

    // no security requirement
    @RequestMapping("/")
    public String home() {
        return "Sample App One is Running\n";
    }

}