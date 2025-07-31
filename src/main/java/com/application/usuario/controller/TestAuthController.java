package com.application.usuario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TestAuthController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Word!!!";
    }

    @GetMapping("/hello-protegido")
    public String helloProtegido() {
        return "Hello Word Protegido!!!";
    }

}
