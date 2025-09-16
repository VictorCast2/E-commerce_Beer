package com.application.presentation.controller.producto;

import com.application.provider.ProductoDataProvider;
import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.producto.request.ProductoCreateRequest;
import com.application.presentation.dto.producto.response.ProductoResponse;
import com.application.service.implementation.producto.ProductoServiceImpl;
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

@WebMvcTest(ProductoAdminController.class)
//@Import(SecurityConfig.class)
class ProductoControllerTest {

    @MockitoBean
    private ProductoServiceImpl productoService;

    @MockitoBean
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void producto() throws Exception {
        // Given
        Usuario usuarioMock = Usuario.builder()
                .numeroIdentificacion("12345")
                .nombres("Theresa Andrea")
                .correo("example@mail.com")
                .rol(Rol.builder().name(ERol.ADMIN).build())
                .build();

        List<ProductoResponse> productoList = ProductoDataProvider.productoResponseListMock();

        String mensaje = "Mensaje de Pruebas";

        // When
        when(usuarioService.encontrarCorreo("example@mail.com")).thenReturn(usuarioMock);
        when(productoService.getProductos()).thenReturn(productoList);

        // Then
        mockMvc.perform(get("/admin/producto/")
                        .param("mensaje", mensaje)
                        .with(user("example@mail.com")
                                .roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("Producto"))
                .andExpect(model().attribute("usuario", usuarioMock))
                .andExpect(model().attribute("productoList", productoList))
                .andExpect(model().attribute("mensaje", mensaje));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addProducto() throws Exception {
        // Given
        GeneralResponse responseMock = new GeneralResponse("Mensaje de Pruebas");
        String mensajeEncode = UriUtils.encode(responseMock.mensaje(), StandardCharsets.UTF_8);

        // When
        when(productoService.createProducto( any(ProductoCreateRequest.class) )).thenReturn(responseMock);

        // Then
        mockMvc.perform(post("/admin/producto/add-producto")
                        .with(csrf())
                        .params(ProductoDataProvider.productoParameters()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/producto/?mensaje=" + mensajeEncode));

        verify(productoService).createProducto(any(ProductoCreateRequest.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProducto() throws Exception {
        // Given
        GeneralResponse responseMock = new GeneralResponse("Mensaje de Pruebas");
        String mensajeEncode = UriUtils.encode(responseMock.mensaje(), StandardCharsets.UTF_8);
        Long id = 1L;

        // When
        when(productoService.updateProducto(any(ProductoCreateRequest.class), anyLong())).thenReturn(responseMock);

        // Then
        mockMvc.perform(post("/admin/producto/update-producto/{id}", id)
                        .with(csrf())
                        .params(ProductoDataProvider.productoParameters()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/producto/?mensaje=" + mensajeEncode));

        verify(productoService).updateProducto(any(ProductoCreateRequest.class), anyLong());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void disableProducto() throws Exception {
        // Given
        GeneralResponse responseMock = new GeneralResponse("Mensaje de Pruebas");
        String mensajeEncode = UriUtils.encode(responseMock.mensaje(), StandardCharsets.UTF_8);
        Long id = 1L;

        // When
        when(productoService.disableProducto( anyLong() )).thenReturn(responseMock);

        // Then
        mockMvc.perform(post("/admin/producto/disable-producto/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/producto/?mensaje=" + mensajeEncode));

        verify(productoService).disableProducto( anyLong() );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProducto() throws Exception {
        // Given
        GeneralResponse responseMock = new GeneralResponse("Mensaje de Pruebas");
        String mensajeEncode = UriUtils.encode(responseMock.mensaje(), StandardCharsets.UTF_8);
        Long id = 1L;

        // When
        when(productoService.deleteProducto( anyLong() )).thenReturn(responseMock);

        // Then
        mockMvc.perform(post("/admin/producto/delete-producto/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/producto/?mensaje=" + mensajeEncode));

        verify(productoService).deleteProducto( anyLong() );
    }
}