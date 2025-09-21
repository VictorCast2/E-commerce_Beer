package com.application.presentation.controller.admin;

import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.categoria.request.CategoriaCreateRequest;
import com.application.presentation.dto.categoria.response.CategoriaResponse;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.service.implementation.categoria.CategoriaServiceImpl;
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
@RequestMapping("/admin/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaServiceImpl categoriaService;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping("/")
    public String Categoria(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "mensaje", required = false) String mensaje,
            Model model
    ) {

        Usuario usuario = usuarioService.getUsuarioByCorreo(userDetails.getUsername());
        List<CategoriaResponse> categoriaResponses = categoriaService.getCategorias();

        model.addAttribute("usuario", usuario);
        model.addAttribute("categoriaList", categoriaResponses);
        model.addAttribute("mensaje", mensaje);

        return "Categoria";
    }

    @PostMapping("/add-categoria")
    public String addcategoria(@ModelAttribute @Valid CategoriaCreateRequest categoriaRequest) {
        GeneralResponse response = categoriaService.createCategoria(categoriaRequest);
        String mensaje = response.mensaje();

        return "redirect:/admin/categoria/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("update-categoria/{id}")
    public String updateCategoria(
            @ModelAttribute @Valid CategoriaCreateRequest categoriaRequest,
            @PathVariable Long id
    ) {
        GeneralResponse response = categoriaService.updateCategoria(categoriaRequest, id);
        String mensaje = response.mensaje();

        return "redirect:/admin/categoria/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("disable-categoria/{id}")
    public String disableCategoria(@PathVariable Long id) {
        GeneralResponse response = categoriaService.disableCategoria(id);
        String mensaje = response.mensaje();

        return "redirect:/admin/categoria/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("delete-categoria/{id}")
    public String deleteCategoria(@PathVariable Long id) {
        GeneralResponse response = categoriaService.deleteCategoria(id);
        String mensaje = response.mensaje();

        return "redirect:/admin/categoria/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

}
