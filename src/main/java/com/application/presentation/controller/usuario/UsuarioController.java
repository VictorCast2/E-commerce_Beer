package com.application.presentation.controller.usuario;

import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.usuario.request.EditarUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioServiceImpl;

    @PostMapping("/eliminar")
    public String postEliminar(@Valid @RequestParam String correo,
                               RedirectAttributes redirectAttributes, Model model) {
        try {
            UsuarioResponse response = usuarioServiceImpl.deleteUsuario(correo);
            redirectAttributes.addFlashAttribute("delete", response);
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Eliminar";
        }
    }

    @GetMapping("/editar")
    public String getEditar(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {
        Usuario usuario = usuarioServiceImpl.encontrarCorreo(userDetails.getUsername());
        model.addAttribute("usuario", usuario);
        model.addAttribute("mensaje", mensaje);
        return "Editar";
    }

    @PostMapping("/editar")
    public String postEditar(@ModelAttribute @Valid EditarUsuarioRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        UsuarioResponse response = usuarioServiceImpl.actualizarUsuario(request, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/usuario/editar?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

}