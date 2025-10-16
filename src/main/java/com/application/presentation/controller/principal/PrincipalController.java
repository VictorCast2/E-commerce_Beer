package com.application.presentation.controller.principal;

import com.application.configuration.Custom.CustomUserPrincipal;
import com.application.persistence.entity.producto.Producto;
import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.producto.response.ProductoResponse;
import com.application.service.implementation.producto.ProductoServiceImpl;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class PrincipalController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private ProductoServiceImpl productoService;

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
    public String Ver(Model model) {
        // Simulación de datos (en producción vendrán de la base de datos)
        model.addAttribute("subcategory", "Licores");
        model.addAttribute("category", "Whisky");
        model.addAttribute("productName", "Jack Daniel’s Old No. 7");

        return "Ver";
    }

    @GetMapping("/ver/{id}")
    public String Ver(@PathVariable Long id, Model model) {
        Producto producto = productoService.getProductoById(id);
        model.addAttribute("subcategory", producto.getDescripcion());
        model.addAttribute("category", producto.getCategorias());
        model.addAttribute("productName", producto.getNombre());
        return "Ver";
    }

    @GetMapping("/descripcion-producto/{productoId}")
    public String getDescripcionProducto(@AuthenticationPrincipal CustomUserPrincipal principal,
                                         @PathVariable Long productoId,
                                         Model model) {
        Usuario usuario = usuarioService.getUsuarioByCorreo(principal.getCorreo());
        ProductoResponse productoResponse = productoService.getProductoResponseById(productoId);

        List<ProductoResponse> productosActivos = productoService.getProductosActivos();
        Collections.shuffle(productosActivos);
        List<ProductoResponse> productosAleatorios = productosActivos.stream().limit(4).toList();

        model.addAttribute("usuario", usuario);
        model.addAttribute("producto", productoResponse);
        model.addAttribute("productoList", productosAleatorios);
        return "Aqui la vista de desccripcion del producto";
    }

}