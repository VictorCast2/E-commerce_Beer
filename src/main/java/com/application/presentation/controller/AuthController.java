package com.application.presentation.controller;

import com.application.persistence.repository.RolRepository;
import com.application.presentation.dto.usuario.request.AuthLoginRequest;
import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.service.implementation.usuario.UsuarioServicesImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UsuarioServicesImpl usuarioServicesImpl;

    @Autowired
    private final RolRepository rolRepository;

    @GetMapping("/")
    public String index() {
        return "Index";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "Login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute AuthLoginRequest loginRequest, Model model) {
        try {
            usuarioServicesImpl.loginUser(loginRequest);
            return "redirect:/"; // Redirige a la página principal tras iniciar sesión
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/login"; // Vuelve al login si hay error
        }
    }

    @GetMapping("/sign_up")
    public String getSignUp() {
        return "Registro";
    }

    @PostMapping("/sign_up")
    public String postSignUp(@Valid @RequestBody CreacionUsuarioRequest request, Model model) {
        try {
            usuarioServicesImpl.crearUsuario(request);
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", rolRepository.findAll());
            return "redirect:/auth/sign_up";
        }
    }

}