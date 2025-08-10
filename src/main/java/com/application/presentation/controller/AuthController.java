package com.application.presentation.controller;

import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("/")
    public String index() {
        return "Index";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "Login";
    }

    @GetMapping("/sign_up")
    public String getSignUp() {
        return "Registro";
    }

    @PostMapping("/sign_up")
    public String postSignUp(@Valid @RequestBody CreacionUsuarioRequest request,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        usuarioServiceImpl.crearUsuario(request);
        return "redirect:/auth/login";
    }

}