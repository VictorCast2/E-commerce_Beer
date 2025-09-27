package com.application.presentation.controller.principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PrincipalController {

    @GetMapping
    public String index() {
        return "Index";
    }

    @GetMapping("/paks")
    public String pack() {
        return "Pack";
    }

    @GetMapping("/productos")
    public String producto() {
        return "Productos";
    }

    @GetMapping("/blog")
    public String blog() {
        return "Blog";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "Contactos";
    }

    @GetMapping("/carrito")
    public String carrito() {
        return "Carrito";
    }

    @GetMapping("/favorito")
    public String favorito() {
        return "Favorito";
    }

    @GetMapping("/ver")
    public String ver() {
        return "Ver";
    }

}