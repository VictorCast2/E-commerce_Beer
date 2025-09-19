package com.application.presentation.controller.principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @GetMapping("/login")
    public String getLogin(Model model,
                           @RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           @RequestParam(value = "success", required = false) String success) {
        if (error != null) {
            model.addAttribute("mensajeError", error);
        }
        if (logout != null) {
            model.addAttribute("mensajeExitoso", "Sesión cerrada correctamente");
        }
        if (success != null) {
            model.addAttribute("loginSuccess", true);
        }
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
