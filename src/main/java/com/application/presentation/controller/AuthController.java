package com.application.presentation.controller;

import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
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
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping()
    public String index(Model model) {
        return "Index";
    }

    @GetMapping("/auth/login")
    public String getLogin(Model model,
                           @RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           @RequestParam(value = "success", required = false) String success) {
        if (error != null) {
            model.addAttribute("mensajeError", "Usuario o contraseña inválidos");
        }
        if (logout != null) {
            model.addAttribute("mensajeExitoso", "Sesión cerrada correctamente");
        }
        if (success != null) {
            model.addAttribute("loginSuccess", true);
        }
        return "Login";
    }

    @GetMapping("/auth/sign_up")
    public String getSignUp(Model model,
                            @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("mensajeError", "Usuario ya existe en la base de datos");
        }
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
        UsuarioResponse response = usuarioServiceImpl.crearUsuario(request);
        model.addAttribute("mensajeExitoso", response.mensaje());
        return "redirect:/auth/login";
    }

}