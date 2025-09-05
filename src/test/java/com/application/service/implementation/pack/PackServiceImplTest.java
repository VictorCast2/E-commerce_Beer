package com.application.service.implementation.pack;

import com.application.persistence.entity.pack.Pack;
import com.application.persistence.entity.pack.enums.ETipo;
import com.application.persistence.entity.producto.Producto;
import com.application.persistence.repository.CategoriaRepository;
import com.application.persistence.repository.PackRepository;
import com.application.persistence.repository.ProductoRepository;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.pack.request.PackCreateRequest;
import com.application.presentation.dto.pack.response.PackResponse;
import com.application.provider.PackDataProvider;
import com.application.provider.ProductoDataProvider;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackServiceImplTest {

    @Mock
    private PackRepository packRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private PackServiceImpl packService;

    @Test
    void getPackById() {
        // Given
        Long id = 1L;

        // When
        when(packRepository.findById(anyLong())).thenReturn(PackDataProvider.packMock());
        Pack resultado = packService.getPackById(id);

        // Then
        assertNotNull(resultado);
        assertEquals("Pack 1", resultado.getNombre());
        assertEquals("Primer pack de pruebas", resultado.getDescripcion());
        assertTrue(resultado.isActivo());

        verify(packRepository).findById(anyLong());
    }

    @Test
    void getPackByIdError() {
        // Given
        Long id = 99L;
        when(packRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When - Then
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                packService.getPackById(id)
        );

        assertEquals("Error: El pack con id: " + id + " no existe", ex.getMessage());
        verify(packRepository).findById( anyLong() );
    }

    @Test
    void getPackResponseById() {
        // Given
        Long id = 1L;

        // When
        when(packRepository.findById( anyLong() )).thenReturn(PackDataProvider.packMock());
        PackResponse resultado = packService.getPackResponseById(id);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Pack 1", resultado.nombre());
        assertEquals("Primer pack de pruebas", resultado.descripcion());

        verify(packRepository).findById(anyLong());
    }

    @Test
    void getPacks() {
        // When
        when(packRepository.findAll()).thenReturn(PackDataProvider.packListMock());
        List<PackResponse> resultado = packService.getPacks();

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(5, resultado.size());
        assertEquals("Pack 3", resultado.get(2).nombre());
        assertEquals("Tercer pack de prueba", resultado.get(2).descripcion());

        verify(packRepository).findAll();
    }

    @Test
    void getPacksActivos() {
        // When
        when(packRepository.findByActivoTrue()).thenReturn(PackDataProvider.packActivoListMock());
        List<PackResponse> resultado = packService.getPacksActivos();

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(3, resultado.size());
        assertEquals("Pack 3", resultado.get(2).nombre());
        assertEquals("Tercer pack de prueba", resultado.get(2).descripcion());

        verify(packRepository).findByActivoTrue();
    }

    @Test
    void getPacksByCategoriaId() {
        // Given
        Long id = 1L;

        // When
        when(packRepository.findByCategorias_CategoriaId( anyLong() )).thenReturn(PackDataProvider.packListByCategoriaIdMock());
        List<PackResponse> resultado = packService.getPacksByCategoriaId(id);

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(3, resultado.size());
        assertEquals("Pack 2", resultado.get(1).nombre());
        assertEquals("Segundo pack de prueba", resultado.get(1).descripcion());

        verify(packRepository).findByCategorias_CategoriaId( anyLong() );
    }

    @Test
    void addPack() {
        // Given
        PackCreateRequest packRequest = PackDataProvider.newPackMock();

        // When
        when(categoriaRepository.findAllById( anyList() )).thenReturn(PackDataProvider.categoriaListMock());
        when(productoRepository.findById( anyLong() )).thenReturn(PackDataProvider.productoMock());

        GeneralResponse resultado = packService.addPack(packRequest);

        // Then
        ArgumentCaptor<Pack> argumentCaptor = ArgumentCaptor.forClass(Pack.class);
        verify(packRepository).save(argumentCaptor.capture());

        assertEquals("Pack 1", argumentCaptor.getValue().getNombre());
        assertEquals(1, argumentCaptor.getValue().getPackProductos().size());
        assertEquals(250, argumentCaptor.getValue().getPackProductos().iterator().next().getProducto().getStock());
        assertEquals(3, argumentCaptor.getValue().getCategorias().size());
        assertEquals("Pack creado exitosamente", resultado.mensaje());

        verify(categoriaRepository).findAllById( anyList() );
        verify(productoRepository, times(2)).findById( anyLong() );
    }

    @Test
    void addPack_StockInsuficiente() {
        // Given
        PackCreateRequest packRequest = PackDataProvider.newPackMock();
        Optional<Producto> producto = ProductoDataProvider.productoMock();
        producto.get().setStock(100);

        // When
        when(categoriaRepository.findAllById( anyList() )).thenReturn(PackDataProvider.categoriaListMock());
        when(productoRepository.findById( anyLong() )).thenReturn(producto);

        GeneralResponse resultado = packService.addPack(packRequest);

        // Then
        assertEquals("No hay suficiente stock del producto: " + producto.get().getNombre(), resultado.mensaje());

        verify(packRepository, never()).save( any(Pack.class) );
    }

    @Test
    void addPack_ProductoNoEncontrado() {
        // Given
        PackCreateRequest packRequest = PackDataProvider.newPackMock();

        when(categoriaRepository.findAllById( anyList() )).thenReturn(PackDataProvider.categoriaListMock());
        when(productoRepository.findById( anyLong() )).thenReturn(Optional.empty());

        // When - Then
        assertThrows(EntityNotFoundException.class, () -> packService.addPack(packRequest));

        verify(packRepository, never()).save( any(Pack.class) );
    }

    @Test
    void updatePack() {
        // Given
        PackCreateRequest packRequest = PackDataProvider.newPackMock();
        Long id = 1L;

        when(packRepository.findById( anyLong() )).thenReturn(PackDataProvider.packMock());
        when(categoriaRepository.findAllById( anyList() )).thenReturn(PackDataProvider.categoriaListMock());
        when(productoRepository.findById( anyLong() )).thenReturn(PackDataProvider.productoMock());

        // When
        GeneralResponse resultado = packService.updatePack(packRequest, id);

        // Then
        ArgumentCaptor<Pack> argumentCaptor = ArgumentCaptor.forClass(Pack.class);
        verify(packRepository).save(argumentCaptor.capture());

        assertEquals("imagen 1", argumentCaptor.getValue().getImagen());
        assertEquals(ETipo.PACK, argumentCaptor.getValue().getETipo());
        assertEquals("Categoria 1", argumentCaptor.getValue().getCategorias().stream()
                .findFirst()
                .orElseThrow()
                .getNombre());
        assertEquals("Producto 1", argumentCaptor.getValue().getPackProductos().iterator().next().getProducto().getNombre());
        assertEquals(250, argumentCaptor.getValue().getPackProductos().iterator().next().getProducto().getStock());
        assertEquals("Pack actualizado exitosamente", resultado.mensaje());

        verify(packRepository).findById( anyLong() );
        verify(categoriaRepository).findAllById( anyList() );
        verify(productoRepository, times(2)).findById( anyLong() );
    }

    @Test
    void updatePack_StockInsuficiente() {
        // Given
        PackCreateRequest packRequest = PackDataProvider.newPackMock();
        Long id = 1L;

        Optional<Producto> producto = PackDataProvider.productoMock();
        producto.get().setStock(100);

        when(packRepository.findById( anyLong() )).thenReturn(PackDataProvider.packMock());
        when(categoriaRepository.findAllById( anyList() )).thenReturn(PackDataProvider.categoriaListMock());
        when(productoRepository.findById( anyLong() )).thenReturn( producto );

        // When
        GeneralResponse resultado = packService.updatePack(packRequest, id);

        // Then
        assertEquals("No hay suficiente stock del producto: " + producto.get().getNombre(), resultado.mensaje());

        verify(packRepository, never()).save( any(Pack.class) );
    }

    @Test
    void updatePack_ProductoNoEncontrado() {
        // Given
        PackCreateRequest packRequest = PackDataProvider.newPackMock();
        Long id = 1L;

        when(packRepository.findById( anyLong() )).thenReturn(PackDataProvider.packMock());
        when(categoriaRepository.findAllById( anyList() )).thenReturn(PackDataProvider.categoriaListMock());
        when(productoRepository.findById( anyLong() )).thenReturn(Optional.empty());

        // When - Then
        assertThrows(EntityNotFoundException.class, () -> packService.updatePack(packRequest, id));

        verify(packRepository, never()).save( any(Pack.class) );
    }

    @Test
    void disablePack() {
        // Given
        Long id = 1L;

        when(packRepository.findById( anyLong() )).thenReturn(PackDataProvider.packMock());

        // When
        GeneralResponse resultado = packService.disablePack(id);

        // Then
        ArgumentCaptor<Pack> argumentCaptor = ArgumentCaptor.forClass(Pack.class);
        verify(packRepository).save(argumentCaptor.capture());

        assertFalse(argumentCaptor.getValue().isActivo());
        assertEquals("Producto deshabilitado exitosamente", resultado.mensaje());
    }

    @Test
    void deletePack() {
        // Given
        Long id = 1L;

        when(packRepository.findById( anyLong() )).thenReturn(PackDataProvider.packMock());

        // When
        GeneralResponse resultado = packService.deletePack(id);

        // Then
        ArgumentCaptor<Pack> argumentCaptor = ArgumentCaptor.forClass(Pack.class);
        verify(packRepository).delete(argumentCaptor.capture());

        assertEquals(1L, argumentCaptor.getValue().getPackId());
        assertEquals("Pack eliminado exitosamente", resultado.mensaje());
    }

    @Test
    void toResponse() {
        // Given
        Pack packMock = PackDataProvider.packMock().get();

        // When
        PackResponse resultado = packService.toResponse(packMock);

        // Then
        assertNotNull(resultado);
        assertEquals("Pack 1", resultado.nombre());
        assertEquals("Primer pack de pruebas", resultado.descripcion());
        assertEquals(50, resultado.stock());
    }
}