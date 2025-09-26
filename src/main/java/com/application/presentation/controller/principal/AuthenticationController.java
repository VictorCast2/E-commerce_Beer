package com.application.presentation.controller.principal;

import com.application.configuration.Custom.CustomUserPrincipal;
import com.application.presentation.dto.empresa.request.CreateEmpresaRequest;
import com.application.presentation.dto.usuario.request.CompleteUsuarioProfileRequest;
import com.application.service.interfaces.empresa.EmpresaService;
import com.application.service.interfaces.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpresaService empresaService;

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

    @GetMapping("/completar-registro")
    public String CompletarRegistro(Model model, @RequestParam(value = "mensaje", required = false) String mensaje) {
        model.addAttribute("mensaje", mensaje); // mensaje para el alert
        return "CompletarRegistro";
    }

    @PostMapping("/completar-registro")
    public String postCompletarPerfil(@AuthenticationPrincipal CustomUserPrincipal principal,
            @ModelAttribute @Valid CompleteUsuarioProfileRequest completeProfileRequest,
            boolean registrarEmpresa) {
        usuarioService.completeUserProfile(principal, completeProfileRequest);
        if (registrarEmpresa) {
            return "redirect:/auth/registrar-empresa";
        }
        return "redirect:/";
    }

    @GetMapping("/registrar-empresa")
    public String RegistrarEmpresa(Model model, @RequestParam(value = "mensaje", required = false) String mensaje) {
        model.addAttribute("mensaje", mensaje); // mensaje para el alert
        return "RegistrarEmpresa";
    }

    @PostMapping("/registrar-empresa")
    public String postCreateEmpresa(@AuthenticationPrincipal CustomUserPrincipal principal,
            @ModelAttribute @Valid CreateEmpresaRequest createEmpresaRequest) {
        empresaService.createEmpresa(principal, createEmpresaRequest);
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