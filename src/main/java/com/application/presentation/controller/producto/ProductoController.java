package com.application.presentation.controller.producto;

import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.producto.request.ProductoCreateRequest;
import com.application.presentation.dto.producto.response.ProductoResponse;
import com.application.service.implementation.producto.ProductoServiceImpl;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/admin/Producto")
public class ProductoController {

    @Autowired
    private ProductoServiceImpl productoService;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping("/")
    public String Producto(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "mensaje", required = false) String mensaje,
            Model model
            ) {

        Usuario usuario = usuarioService.getUsuarioByCorreo(userDetails.getUsername());
        List<ProductoResponse> productoList = productoService.getProductos();

        model.addAttribute("usuario", usuario);
        model.addAttribute("productoList", productoList);
        model.addAttribute("mensaje", mensaje);

        return "Producto";
    }

    @PostMapping("/add-Producto")
    public String addProducto(@ModelAttribute @Valid ProductoCreateRequest productoRequest) {
        GeneralResponse response = productoService.addProducto(productoRequest);
        String mensaje = response.mensaje();

        return "redirect:/admin/Producto/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("/update-Producto/{id}")
    public String updateProducto(@ModelAttribute @Valid ProductoCreateRequest productoRequest, @PathVariable Long id) {
        GeneralResponse response = productoService.updateProducto(productoRequest, id);
        String mensaje = response.mensaje();

        return "redirect:/admin/Producto/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("disable-Producto/{id}")
    public String disableProducto(@PathVariable Long id) {
        GeneralResponse response = productoService.disableProducto(id);
        String mensaje = response.mensaje();

        return "redirect:/admin/Producto/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("delete-Producto/{id}")
    public String deleteProducto(@PathVariable Long id) {
        GeneralResponse response = productoService.deleteProducto(id);
        String mensaje = response.mensaje();

        return "redirect:/admin/Producto/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

 }
