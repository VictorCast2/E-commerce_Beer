package com.application.presentation.controller.usuario;

import com.application.presentation.dto.usuario.request.EditarUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private final UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("/eliminar")
    public String geteliminar() {
        return "Index";
    }

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
    public String getEditar() {
        return "Editar";
    }

    @PostMapping("/editar")
    public String postEditar(@RequestParam("correo") String correo,
                             @ModelAttribute EditarUsuarioRequest request,
                             Model model) {
        try {
            UsuarioResponse response = usuarioServiceImpl.actualizarUsuario(correo, request);
            model.addAttribute("mensaje", response.mensaje());
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/usuario/editar";
        }
    }

}