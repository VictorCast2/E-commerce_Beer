package com.application.persistence.repository;

import com.application.provider.ProductoDataProvider;
import com.application.persistence.entity.producto.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByActivoTrue() {
        // When
        List<Producto> productosActivos = productoRepository.findByActivoTrue();

        // Then
        assertEquals(2, productosActivos.size());
        assertEquals("Producto 1", productosActivos.getFirst().getNombre());
        assertTrue(productosActivos.getFirst().isActivo());
        assertFalse(productosActivos.isEmpty());
    }
}