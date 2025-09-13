package com.application.presentation.controller.producto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoServiceImpl productoService;
    private final UsuarioServiceImpl usuarioService;

    @GetMapping("/")
    public String Producto(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "mensaje", required = false) String mensaje,
            Model model
            ) {

        Usuario usuario = usuarioService.encontrarCorreo(userDetails.getUsername());
        List<ProductoResponse> productoList = productoService.getProductos();

        model.addAttribute("usuario", usuario);
        model.addAttribute("productoList", productoList);
        model.addAttribute("mensaje", mensaje);

        return "Producto";
    }
  
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