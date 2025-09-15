package com.application.presentation.controller.pack;

import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.pack.request.PackCreateRequest;
import com.application.presentation.dto.pack.response.PackResponse;
import com.application.service.implementation.pack.PackServiceImpl;
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
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/pack")
public class PackController {

    @Autowired
    private PackServiceImpl packService;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping("/")
    public String Pack(@AuthenticationPrincipal UserDetails userDetails,
                       @RequestParam(value = "mensaje", required = false) String mensaje,
                       Model model) {

        Usuario usuario = usuarioService.encontrarCorreo(userDetails.getUsername());
        List<PackResponse> packList = packService.getPacks();

        model.addAttribute("usuario", usuario);
        model.addAttribute("packList", packList);
        model.addAttribute("mensaje", mensaje);

        return "Pack";
    }

    @PostMapping("/add-pack")
    public String addPack(@ModelAttribute @Valid PackCreateRequest packRequest) {
        GeneralResponse response = packService.addPack(packRequest);
        String mensaje = response.mensaje();

        return "redirect:/admin/pack/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("/update-pack/{id}")
    public String updatePack(@ModelAttribute @Valid PackCreateRequest packRequest, @PathVariable Long id) {
        GeneralResponse response = packService.updatePack(packRequest, id);
        String mensaje = response.mensaje();

        return "redirect:/admin/pack/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("/disable-pack/{id}")
    public String disablePack(@PathVariable Long id) {
        GeneralResponse response = packService.disablePack(id);
        String mensaje = response.mensaje();

        return "redirect:/admin/pack/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("/delete-pack/{id}")
    public String deletePack(@PathVariable Long id) {
        GeneralResponse response = packService.deletePack(id);
        String mensaje = response.mensaje();

        return "redirect:/admin/pack/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    // Reasignar este método al controlador de la página principal
    @PostMapping("/descripcion-pack/{id}")
    public String getDescripcionPack(@AuthenticationPrincipal UserDetails userDetails,
                                     @PathVariable Long id,
                                     Model model) {
        Usuario usuario = usuarioService.encontrarCorreo(userDetails.getUsername());
        PackResponse packResponse = packService.getPackResponseById(id);
        List<PackResponse> packList = packService.getPacksActivos();
        Collections.shuffle(packList);
        List<PackResponse> packAleatorios = packList.stream().limit(4).toList();

        model.addAttribute("usuario", usuario);
        model.addAttribute("pack", packResponse);
        model.addAttribute("packList", packAleatorios);

        return "descripcion";
    }

}
