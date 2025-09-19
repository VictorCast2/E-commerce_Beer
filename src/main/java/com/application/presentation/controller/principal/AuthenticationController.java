package com.application.presentation.controller.principal;

import com.application.presentation.dto.usuario.request.CompleteUsuarioProfileRequest;
import com.application.service.interfaces.usuario.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private UsuarioService usuarioService;

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

    @GetMapping("/completar-perfil")
    public String getCompletarPerfil(Model model, @RequestParam(value = "mensaje", required = false) String mensaje) {
        model.addAttribute("mensaje", mensaje); // mensaje para el alert
        return "CompletarPerfil";
    }

    @PostMapping("/completar-perfil")
    public String postCompletarPerfil(@AuthenticationPrincipal OAuth2User principal,
                                  @ModelAttribute @Valid CompleteUsuarioProfileRequest completeProfileRequest,
                                  boolean registrarEmpresa) {
        String correo = principal.getName();
        usuarioService.completeUserProfile(principal, completeProfileRequest);

        if (registrarEmpresa) {
            return "redirect:/registrar-empresa";
        }

        return "redirect:/";
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
