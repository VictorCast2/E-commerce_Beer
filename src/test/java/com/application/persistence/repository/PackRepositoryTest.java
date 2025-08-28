package com.application.persistence.repository;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.pack.Pack;
import com.application.persistence.entity.producto.Producto;
import com.application.provider.PackDataProvider;
import com.application.provider.ProductoDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.parameters.P;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PackRepositoryTest {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        List<Pack> packs = PackDataProvider.packList();
        packs.forEach(entityManager::persist);
        entityManager.flush();
    }

    @Test
    void findByActivoTrue() {
        // When
        List<Pack> packsActivos = packRepository.findByActivoTrue();

        // Then
        assertEquals(2, packsActivos.size());
        assertTrue(packsActivos.getFirst().isActivo());
        assertEquals("Pack 1", packsActivos.getFirst().getNombre());
        assertFalse(packsActivos.isEmpty());
    }

    @Test
    void findByCategorias_CategoriaId() {
        // Given
        Long id = 1L;

        // When
        List<Pack> packsByCategoria = packRepository.findByCategorias_CategoriaId(id);
        Categoria categoria = categoriaRepository.findById(id).orElseThrow();

        // Then
        assertEquals(2, packsByCategoria.size());
        assertEquals("Categoria 1", categoria.getNombre());
        assertEquals("Pack 1", packsByCategoria.getFirst().getNombre());
        assertEquals("Pack 3", packsByCategoria.getLast().getNombre());
        assertFalse(packsByCategoria.isEmpty());
    }
}