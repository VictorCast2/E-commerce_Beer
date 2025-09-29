package com.application.presentation.controller.principal;

import com.application.configuration.Custom.CustomUserPrincipal;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import com.application.presentation.dto.empresa.request.CreateEmpresaRequest;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.general.response.RegisterResponse;
import com.application.presentation.dto.usuario.request.CompleteUsuarioProfileRequest;
import com.application.presentation.dto.usuario.request.CreateUsuarioRequest;
import com.application.service.interfaces.empresa.EmpresaService;
import com.application.service.interfaces.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/login")
    public String login(Model model,
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
    public String CompletarRegistro(@AuthenticationPrincipal CustomUserPrincipal principal,
                                    @RequestParam(value = "mensaje", required = false) String mensaje,
                                    Model model) {
        Usuario usuario = this.usuarioService.getUsuarioByCorreo(principal.getCorreo());
        model.addAttribute("usuario", usuario);
        model.addAttribute("tiposIdentificacion", EIdentificacion.values());
        model.addAttribute("mensaje", mensaje); // mensaje para el alert
        return "CompletarRegistro";
    }

    @PostMapping("/completar-registro")
    public String postCompletarPerfil(@AuthenticationPrincipal CustomUserPrincipal principal,
            @ModelAttribute @Valid CompleteUsuarioProfileRequest completeProfileRequest,
            @RequestParam(name = "registrarEmpresa", defaultValue = "false") boolean registrarEmpresa) {
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
    public String Registro(@RequestParam(value = "mensaje", required = false) String mensaje,
                           @RequestParam(value = "success", required = false) Boolean success,
                           Model model) {
        model.addAttribute("tiposIdentificacion", EIdentificacion.values());
        model.addAttribute("mensaje", mensaje); // mensaje a mostrar
        model.addAttribute("success", success); // boolean que determina si se obtuvo un mensaje de exito o error
        return "Registro";
    }

    @PostMapping("/registro")
    public String postRegistro(@ModelAttribute @Valid CreateUsuarioRequest usuarioRequest, BindingResult result) {

        if (result.hasErrors()) {
            String mensaje = UriUtils.encode("Error en los datos del formulario", StandardCharsets.UTF_8);
            return "redirect:/auth/registro?mensaje=" + mensaje + "&success=false";
        }
        RegisterResponse response = usuarioService.createUser(usuarioRequest);
        String mensaje = UriUtils.encode(response.mensaje(), StandardCharsets.UTF_8);
        return "redirect:/auth/registro?mensaje=" + mensaje + "&success=" + response.success();
    }

    @GetMapping("/recordar-contrasenna")
    public String recordarContrasenna() {
        return "RecordarContrasena";
    }

}