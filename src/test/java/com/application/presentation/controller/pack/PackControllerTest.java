package com.application.presentation.controller.pack;

import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.pack.request.PackCreateRequest;
import com.application.presentation.dto.pack.response.PackResponse;
import com.application.provider.PackDataProvider;
import com.application.service.implementation.pack.PackServiceImpl;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PackController.class)
class PackControllerTest {

    @MockitoBean
    private PackServiceImpl packService;

    @MockitoBean
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void pack() throws Exception {
        // Given
        Usuario usuarioMock = Usuario.builder()
                .cedula("12345")
                .nombres("Theresa Andrea")
                .correo("example@mail.com")
                .rol(Rol.builder().name(ERol.ADMIN).build())
                .build();

        List<PackResponse> packList = PackDataProvider.packResponseListMock();

        String mensaje = "Mensaje de Prueba";

        when(usuarioService.getUsuarioByCorreo("example@mail.com")).thenReturn(usuarioMock);
        when(packService.getPacks()).thenReturn(packList);

        // When - Then
        mockMvc.perform(get("/admin/pack/")
                        .param("mensaje", mensaje)
                        .with(user("example@mail.com")
                                .roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("Pack"))
                .andExpect(model().attribute("usuario", usuarioMock))
                .andExpect(model().attribute("packList", packList))
                .andExpect(model().attribute("mensaje", mensaje));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addPack() throws Exception {
        // Given
        GeneralResponse responseMock = new GeneralResponse("Mensaje de Prueba");
        String mensajeEncode = UriUtils.encode(responseMock.mensaje(), StandardCharsets.UTF_8);

        when(packService.addPack(any(PackCreateRequest.class))).thenReturn(responseMock);

        // Then
        mockMvc.perform(post("/admin/pack/add-pack")
                        .with(csrf())
                        .params(PackDataProvider.packParameters()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pack/?mensaje=" + mensajeEncode));

        verify(packService).addPack(any(PackCreateRequest.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updatePack() throws Exception {
        // Given
        GeneralResponse responseMock = new GeneralResponse("Mensaje de Prueba");
        String mensajeEncode = UriUtils.encode(responseMock.mensaje(), StandardCharsets.UTF_8);
        Long id = 1L;

        when(packService.updatePack(any(PackCreateRequest.class), anyLong())).thenReturn(responseMock);

        // When - Then
        mockMvc.perform(post("/admin/pack/update-pack/{id}", id)
                        .with(csrf())
                        .params(PackDataProvider.packParameters()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pack/?mensaje=" + mensajeEncode));

        verify(packService).updatePack(any(PackCreateRequest.class), anyLong());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void disablePack() throws Exception {
        // Given
        GeneralResponse responseMock = new GeneralResponse("Mensaje de prueba");
        String mensajeEncode = UriUtils.encode(responseMock.mensaje(), StandardCharsets.UTF_8);
        Long id = 1L;

        when(packService.disablePack(anyLong())).thenReturn(responseMock);

        // When - Then
        mockMvc.perform(post("/admin/pack/disable-pack/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pack/?mensaje=" + mensajeEncode));

        verify(packService).disablePack(anyLong());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deletePack() throws Exception {
        // Given
        GeneralResponse responseMock = new GeneralResponse("Mensaje de prueba");
        String mensajeEncode = UriUtils.encode(responseMock.mensaje(), StandardCharsets.UTF_8);
        Long id = 1L;

        when(packService.deletePack(anyLong())).thenReturn(responseMock);

        // When - Then
        mockMvc.perform(post("/admin/pack/delete-pack/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/pack/?mensaje=" + mensajeEncode));

        verify(packService).deletePack( anyLong() );
    }

    @Test
    void getDescripcionPack() {
    }
}