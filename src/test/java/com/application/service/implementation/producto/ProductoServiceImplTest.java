package com.application.service.implementation.producto;

import com.application.ProductoDataProvider;
import com.application.persistence.entity.producto.Producto;
import com.application.persistence.repository.ProductoRepository;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.producto.request.ProductoCreateRequest;
import com.application.presentation.dto.producto.response.ProductoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void getProductoById() {
        // Given
        Long id = 1L;

        // When
        when(productoRepository.findById(anyLong())).thenReturn(ProductoDataProvider.productoMock());
        Producto resultado = productoService.getProductoById(id);

        // Then
        assertNotNull(resultado);
        assertEquals("Producto 1", resultado.getNombre());
        assertEquals("Primer producto de pruebas", resultado.getDescripcion());
        assertTrue(resultado.isActivo());

        verify(productoRepository).findById(anyLong());
    }

    @Test
    void getProductoByIdError() {
        // Given
        Long id = 99L;
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When - Then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () ->
                productoService.getProductoById(id)
        );

        assertEquals("Error: El producto con id: " + id + " no existe", ex.getMessage());
        verify(productoRepository).findById(anyLong());
    }

    @Test
    void getProductos() {
        // When
        when(productoRepository.findAll()).thenReturn(ProductoDataProvider.productoListMock());
        List<ProductoResponse> resultado = productoService.getProductos();

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(5, resultado.size());
        assertEquals("Producto 3", resultado.get(2).nombre());
        assertEquals("Tercer producto de pruebas", resultado.get(2).descripcion());

        verify(productoRepository).findAll();
    }

    @Test
    void getProductosActivos() {
        // When
        when(productoRepository.findByActivoTrue()).thenReturn(ProductoDataProvider.productoActivoListMock());
        List<ProductoResponse> resultado = productoService.getProductosActivos();

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(3, resultado.size());
        assertEquals("Producto 3", resultado.get(2).nombre());
        assertEquals("Tercer producto de pruebas", resultado.get(2).descripcion());

        verify(productoRepository).findByActivoTrue();
    }

    @Test
    void addProducto() {
        // Given
        ProductoCreateRequest productoRequest = ProductoDataProvider.newProductoMock();

        // When
        GeneralResponse resultado = productoService.addProducto(productoRequest);

        // Then
        ArgumentCaptor<Producto> argumentCaptor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).save(argumentCaptor.capture());

        assertEquals("Producto 1", argumentCaptor.getValue().getNombre());
        assertEquals("Producto creado exitosamente", resultado.mensaje());
    }

    @Test
    void updateProducto() {
        // Given
        ProductoCreateRequest productoRequest = ProductoDataProvider.newProductoMock();
        Long id = 1L;

        // When
        when(productoRepository.findById(anyLong())).thenReturn(ProductoDataProvider.productoMock());
        GeneralResponse resultado = productoService.updateProducto(productoRequest, id);

        // Then
        ArgumentCaptor<Producto> argumentCaptor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).findById(anyLong());
        verify(productoRepository).save(argumentCaptor.capture());

        assertNotNull(argumentCaptor);
        assertEquals(1L, argumentCaptor.getValue().getProductoId());
        assertEquals("Producto 1", argumentCaptor.getValue().getNombre());
        assertEquals(1, argumentCaptor.getValue().getPackProductos().size());
        assertEquals("Pack 1", argumentCaptor.getValue().getPackProductos().stream()
                .findFirst()
                .orElseThrow()
                .getPack()
                .getNombre());
        assertEquals("Producto actualizado exitosamente", resultado.mensaje());
    }

    @Test
    void disableProducto() {
        // Given
        Long id = 1L;

        // When
        when(productoRepository.findById(anyLong())).thenReturn(ProductoDataProvider.productoMock());
        GeneralResponse resultado = productoService.disableProducto(id);

        // Then
        ArgumentCaptor<Producto> argumentCaptor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).findById(anyLong());
        verify(productoRepository).save(argumentCaptor.capture());

        assertFalse(argumentCaptor.getValue().isActivo());
        assertEquals("Producto deshabilitado exitosamente", resultado.mensaje());
    }

    @Test
    void deleteProducto() {
        // Given
        Long id = 1L;

        // When
        when(productoRepository.findById(anyLong())).thenReturn(ProductoDataProvider.productoMock());
        GeneralResponse resultado = productoService.deleteProducto(id);

        // Then
        ArgumentCaptor<Producto> argumentCaptor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).findById(anyLong());
        verify(productoRepository).delete(argumentCaptor.capture());

        assertNotNull(argumentCaptor);
        assertEquals(1L, argumentCaptor.getValue().getProductoId());
        assertEquals("Producto eliminado exitosamente", resultado.mensaje());
    }
}