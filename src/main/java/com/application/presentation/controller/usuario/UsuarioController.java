package com.application.presentation.controller.usuario;

import com.application.service.implementation.usuario.UsuarioServicesImpl;
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
    private final UsuarioServicesImpl usuarioServicesImpl;

    @GetMapping("/eliminar")
    public String geteliminar() {
        return "Index";
    }

    @PostMapping("/eliminar")
    public String postEliminar(@RequestParam String correo, Model model) {
        try {
            usuarioServicesImpl.deleteUsuario(correo);
            return "redirect:/auth/login"; // Redirige después de eliminar
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "Eliminar";
        }
    }

}