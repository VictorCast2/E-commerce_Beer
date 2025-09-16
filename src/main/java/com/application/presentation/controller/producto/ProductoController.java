package com.application.presentation.controller.producto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @GetMapping("/favoritos")
    public String productoFavoritos() {
        return "Favorito";
    }

}