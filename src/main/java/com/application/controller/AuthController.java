package com.application.controller;

import com.application.rol.repository.RolRepository;
import com.application.usuario.dto.request.AuthLoginRequest;
import com.application.usuario.dto.request.CreacionUsuarioRequest;
import com.application.usuario.services.UsuarioServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UsuarioServices usuarioServices;

    @Autowired
    private final RolRepository rolRepository;

    @GetMapping("/")
    public String index() {
        return "Index";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("loginRequest", new AuthLoginRequest("", ""));
        return "Login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute AuthLoginRequest loginRequest, Model model) {
        try {
            usuarioServices.loginUser(loginRequest);
            return "redirect:/"; // Redirige a la página principal tras iniciar sesión
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/auth/login"; // Vuelve al login si hay error
        }
    }

    @GetMapping("/sign_up")
    public String getSignUp(Model model) {
        model.addAttribute("registroRequest", new CreacionUsuarioRequest(
                "", "", "", 0, "", "", null));
        model.addAttribute("roles", rolRepository.findAll());
        return "Registro";
    }

    @PostMapping("/sign_up")
    public String postSignUp(@ModelAttribute CreacionUsuarioRequest registroRequest, Model model) {
        try {
            usuarioServices.crearUsuario(registroRequest);
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", rolRepository.findAll());
            return "redirect:/auth/sign_up";
        }
    }

}