package com.application;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.pack.Pack;
import com.application.presentation.dto.request.CategoriaCreateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaDataProvider {

    public static List<Categoria> categoriaList() {

        // ===== Categorías =====
        Categoria categoria1 = Categoria.builder()
                .nombre("Categoria 1")
                .descripcion("Primera categoria de prueba")
                .activo(true)
                .build();

        Categoria categoria2 = Categoria.builder()
                .nombre("Categoria 2")
                .descripcion("Segunda categoria de prueba")
                .activo(false)
                .build();

        Categoria categoria3 = Categoria.builder()
                .nombre("Categoria 3")
                .descripcion("Tercera categoria de prueba")
                .activo(true)
                .build();

        // ===== Packs =====
        Pack pack1 = Pack.builder()
                .nombre("Pack 1")
                .precio(1000)
                .build();

        Pack pack2 = Pack.builder()
                .nombre("Pack 2")
                .precio(1200)
                .build();

        Pack pack3 = Pack.builder()
                .nombre("Pack 3")
                .precio(1500)
                .build();

        // ===== Relacionar packs con categorías =====
        // Categoría 1 → Pack 1 y Pack 2
        pack1.addCategoria(categoria1);
        pack2.addCategoria(categoria1);

        // Categoría 2 e inactiva → sin packs

        // Categoría 3 → Pack 3
        pack3.addCategoria(categoria3);

        return new ArrayList<>(List.of(
                categoria1, categoria2, categoria3
        ));
    }

    public static List<Categoria> categoriaListMock() {

        // ===== Categorías =====
        Categoria categoria1 = Categoria.builder()
                .categoriaId(1L)
                .nombre("Categoria 1")
                .descripcion("Primera categoria de prueba")
                .activo(true)
                .build();

        Categoria categoria2 = Categoria.builder()
                .categoriaId(2L)
                .nombre("Categoria 2")
                .descripcion("Segunda categoria de prueba")
                .activo(false)
                .build();

        Categoria categoria3 = Categoria.builder()
                .categoriaId(3L)
                .nombre("Categoria 3")
                .descripcion("Tercera categoria de prueba")
                .activo(true)
                .build();

        Categoria categoria4 = Categoria.builder()
                .categoriaId(4L)
                .nombre("Categoria 4")
                .descripcion("Cuarta categoria de prueba")
                .activo(false)
                .build();

        Categoria categoria5 = Categoria.builder()
                .categoriaId(5L)
                .nombre("Categoria 5")
                .descripcion("Quinta categoria de prueba")
                .activo(true)
                .build();

        return new ArrayList<>(List.of(
                categoria1, categoria2, categoria3, categoria4, categoria5
        ));
    }

    public static List<Categoria> categoriaActivaListMock() {

        // ===== Categorías =====
        Categoria categoria1 = Categoria.builder()
                .categoriaId(1L)
                .nombre("Categoria 1")
                .descripcion("Primera categoria de prueba")
                .activo(true)
                .build();

        Categoria categoria2 = Categoria.builder()
                .categoriaId(2L)
                .nombre("Categoria 2")
                .descripcion("Segunda categoria de prueba")
                .activo(true)
                .build();

        Categoria categoria3 = Categoria.builder()
                .categoriaId(3L)
                .nombre("Categoria 3")
                .descripcion("Tercera categoria de prueba")
                .activo(true)
                .build();

        return new ArrayList<>(List.of(
                categoria1, categoria2, categoria3
        ));
    }

    public static Optional<Categoria> categoriaMock() {
        return Optional.of(Categoria.builder()
                .categoriaId(1L)
                .nombre("Categoria 1")
                .descripcion("Primera categoria de prueba")
                .activo(true)
                .build());
    }

    public static Optional<Categoria> categoriaConPackMock() {
        Categoria categoria1 = Categoria.builder()
                .categoriaId(1L)
                .nombre("Categoria 1")
                .descripcion("Primera categoria de prueba")
                .activo(true)
                .build();
        Pack pack1 = Pack.builder()
                .nombre("Pack 1")
                .precio(1000)
                .build();
        pack1.addCategoria(categoria1);

        return Optional.of(categoria1);
    }

    public static CategoriaCreateRequest newCategoriaMock() {
        return new CategoriaCreateRequest("Categoria 1", "Primera  categoria de prueba");
    }

    public static long cantidadPackPorCategoria() {
        return 2L;
    }
}
