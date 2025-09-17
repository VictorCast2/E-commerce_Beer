package com.application.presentation.controller.principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @GetMapping("/registro")
    public String Registro() {
        return "Registro";
    }

    @GetMapping("/remember-password")
    public String rememberPassword() {
        return "";
    }
}
