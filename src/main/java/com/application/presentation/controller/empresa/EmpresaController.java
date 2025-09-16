package com.application.presentation.controller.empresa;

import com.application.presentation.dto.empresa.request.CreacionEmpresaRequest;
import com.application.service.implementation.empresa.EmpresaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class EmpresaController {

    private final UsuarioServiceImpl usuarioServiceImpl;
    private final EmpresaServiceImpl empresaServiceImpl;

    @PostMapping("/auth/sign_up/empresa")
    public String postSignUpEmpresa(@Valid @ModelAttribute("usuario") CreacionEmpresaRequest request,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error ->
                    System.out.println(error.getDefaultMessage())
            );
            return "Registro";
        }/*
        empresaServiceImpl.crearEmpresa();
        usuarioServiceImpl.actualizarUsuario()
        */
        return "redirect:/auth/login";
    }

}