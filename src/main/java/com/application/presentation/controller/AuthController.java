package com.application.presentation.controller;

import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("")
    public String index() {
        return "Index";
    }

    @GetMapping("/proteted")
    public String proteted() {
        return "Proteted";
    }

    @GetMapping("/auth/login")
    public String getLogin() {
        return "Login";
    }

    @GetMapping("/auth/sign_up")
    public String getSignUp(Model model) {
        model.addAttribute("usuario", new CreacionUsuarioRequest());
        return "Registro";
    }

    @PostMapping("/auth/sign_up")
    public String postSignUp(@Valid @ModelAttribute("usuario") CreacionUsuarioRequest request,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error ->
                    System.out.println(error.getDefaultMessage())
            );
            return "Registro";
        }

        usuarioServiceImpl.crearUsuario(request);
        return "redirect:/auth/login";

    }

}