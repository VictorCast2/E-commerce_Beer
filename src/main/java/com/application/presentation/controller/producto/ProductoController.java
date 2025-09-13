package com.application.presentation.controller.producto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @GetMapping("/destacados")
    public String productoDestacados() {
        return "ProductosDestacados";
    }

    @GetMapping("/favoritos")
    public String productoFavoritos() {
        return "Favorito";
    }

    @GetMapping("/whiskys")
    public String productoWhiskys() {
        return "Whisky";
    }

    @GetMapping("/tequila_mezcal")
    public String productoTequilaAndMezcal() {
        return "Tequila&Mezcal";
    }

    @GetMapping("/vinos")
    public String productoVinos() {
        return "Vinos";
    }

    @GetMapping("/cervezas")
    public String productoCervezas() {
        return "Cervezas";
    }


}