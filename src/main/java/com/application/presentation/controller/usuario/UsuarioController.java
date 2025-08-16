package com.application.presentation.controller.usuario;

import com.application.configuration.Custom.CustomUserDetails;
import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.usuario.request.EditarUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private final UsuarioServiceImpl usuarioServiceImpl;

    @PostMapping("/eliminar")
    public String postEliminar(@RequestParam String correo,
                               Model model) {
        try {
            usuarioServiceImpl.deleteUsuario(correo);
            return "redirect:/auth/login"; // Redirige después de eliminar
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Eliminar";
        }
    }

    @GetMapping("/editar")
    public String getEditar(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {
        Usuario usuario = usuarioServiceImpl.getUsuarioByCorreo(userDetails.getUsername());
        model.addAttribute("usuario", usuario); // datos del usuario
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios, viajan mediate la url
        return "Editar";
    }

    @PostMapping("/editar")
    public String postEditar(@ModelAttribute @Valid EditarUsuarioRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        UsuarioResponse response = usuarioServiceImpl.actualizarUsuario(request, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/usuario/editar?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

}