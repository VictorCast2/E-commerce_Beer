package com.application.presentation.controller.principal;

import com.application.configuration.Custom.CustomUserPrincipal;
import com.application.persistence.entity.empresa.enums.ESector;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import com.application.presentation.dto.empresa.request.UpdateEmpresaRequest;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.usuario.request.UpdateUsuarioRequest;
import com.application.service.implementation.empresa.EmpresaServiceImpl;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private EmpresaServiceImpl empresaService;

    @GetMapping("/")
    public String perfil(@AuthenticationPrincipal CustomUserPrincipal principal,
                         @RequestParam(value = "mensaje", required = false) String mensaje,
                         Model model) {
        Usuario usuario = usuarioService.getUsuarioByCorreo(principal.getCorreo());
        model.addAttribute("usuario", usuario); // datos del usuario
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios
        model.addAttribute("tiposIdentificacion", EIdentificacion.values());
        model.addAttribute("sectoresEmpresa", ESector.values());
        return "Perfil";
    }

    @PostMapping("/actualizar-datos")
    public String updateUsuario(@AuthenticationPrincipal CustomUserPrincipal principal,
                                @ModelAttribute @Valid UpdateUsuarioRequest usuarioRequest) {
        GeneralResponse response = usuarioService.updateUser(principal, usuarioRequest);
        String mensaje = response.mensaje();
        return "redirect:/perfil/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("/actualizar-empresa")
    public String updateEmpresa(@AuthenticationPrincipal CustomUserPrincipal principal,
                                @ModelAttribute @Valid UpdateEmpresaRequest empresaRequest) {
        GeneralResponse response = empresaService.updateEmpresa(principal, empresaRequest);
        String mensaje = response.mensaje();
        return "redirect:/perfil/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @GetMapping("/completar-registro")
    public String completarRegistro() {
        return "CompletarRegistro";
    }

    @GetMapping("/configuration")
    public String configuracion() {
        return "Configuraciones";
    }

    @GetMapping("/notificaciones")
    public String notificaciones() {
        return "Notificaciones";
    }

    @GetMapping("/pedidos")
    public String Pedidos() {
        return "Pedidos";
    }

}