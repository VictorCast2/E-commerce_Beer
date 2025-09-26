package com.application.presentation.controller.principal;

import com.application.service.implementation.empresa.EmpresaServiceImpl;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private EmpresaServiceImpl empresaService;

    @RequestMapping("/")
    public String perfil() {
        return "Perfil";
    }
}
