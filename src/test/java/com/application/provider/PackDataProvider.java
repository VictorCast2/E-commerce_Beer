package com.application.provider;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.pack.Pack;
import com.application.persistence.entity.pack.enums.ETipo;
import com.application.persistence.entity.producto.Producto;
import com.application.presentation.dto.pack.request.PackCreateRequest;
import com.application.presentation.dto.pack.request.ProductoCantidadRequest;
import com.application.presentation.dto.pack.response.PackCategoriaResponse;
import com.application.presentation.dto.pack.response.PackResponse;
import com.application.presentation.dto.pack.response.ProductoCantidadResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PackDataProvider {

    // Repository
    public static List<Pack> packList() {

        // ===== Paks =====
        Pack pack1 = Pack.builder()
                .nombre("Pack 1")
                .descripcion("Primer pack de prueba")
                .precio(1000)
                .activo(true)
                .build();

        Pack pack2 = Pack.builder()
                .nombre("Pack 2")
                .descripcion("Segundo pack de prueba")
                .precio(1200)
                .activo(false)
                .build();

        Pack pack3 = Pack.builder()
                .nombre("Pack 3")
                .descripcion("Tercer pack de prueba")
                .precio(1500)
                .activo(true)
                .build();

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

        // ===== Relacionar packs con categorías =====
        // Pack 1 → Categoria1, Categoria2, Categoria3
        pack1.addCategoria(categoria1);

        // Pack 2 → Categoria2
        pack2.addCategoria(categoria2);

        // Pack 3 → Categoria1, Categoria3
        pack3.addCategoria(categoria1);
        pack3.addCategoria(categoria3);

        return new ArrayList<>(List.of(
                pack1, pack2, pack3
        ));
    }

    // Service
    public static List<Pack> packListMock() {

        Pack pack1 = Pack.builder()
                .packId(1L)
                .nombre("Pack 1")
                .descripcion("Primer pack de prueba")
                .precio(1000)
                .activo(true)
                .build();

        Pack pack2 = Pack.builder()
                .packId(2L)
                .nombre("Pack 2")
                .descripcion("Segundo pack de prueba")
                .precio(1200)
                .activo(false)
                .build();

        Pack pack3 = Pack.builder()
                .packId(3L)
                .nombre("Pack 3")
                .descripcion("Tercer pack de prueba")
                .precio(1500)
                .activo(true)
                .build();

        Pack pack4 = Pack.builder()
                .packId(4L)
                .nombre("Pack 4")
                .descripcion("Cuarto pack de prueba")
                .precio(1800)
                .activo(false)
                .build();

        Pack pack5 = Pack.builder()
                .packId(5L)
                .nombre("Pack 5")
                .descripcion("Quinto pack de prueba")
                .precio(2000)
                .activo(true)
                .build();


        return List.of(
                pack1, pack2, pack3, pack4, pack5
        );
    }

    public static List<Pack> packActivoListMock() {

        Pack pack1 = Pack.builder()
                .packId(1L)
                .nombre("Pack 1")
                .descripcion("Primer pack de prueba")
                .precio(1000)
                .activo(true)
                .build();

        Pack pack2 = Pack.builder()
                .packId(2L)
                .nombre("Pack 2")
                .descripcion("Segundo pack de prueba")
                .precio(1200)
                .activo(true)
                .build();

        Pack pack3 = Pack.builder()
                .packId(3L)
                .nombre("Pack 3")
                .descripcion("Tercer pack de prueba")
                .precio(1500)
                .activo(true)
                .build();

        return List.of(
                pack1, pack2, pack3
        );
    }

    public static List<Pack> packListByCategoriaIdMock() {

        // ===== Paks =====
        Pack pack1 = Pack.builder()
                .packId(1L)
                .nombre("Pack 1")
                .descripcion("Primer pack de prueba")
                .precio(1000)
                .activo(true)
                .build();

        Pack pack2 = Pack.builder()
                .packId(2L)
                .nombre("Pack 2")
                .descripcion("Segundo pack de prueba")
                .precio(1200)
                .activo(false)
                .build();

        Pack pack3 = Pack.builder()
                .packId(3L)
                .nombre("Pack 3")
                .descripcion("Tercer pack de prueba")
                .precio(1500)
                .activo(true)
                .build();

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

        // ===== Relacionar packs con categorías =====
        // Pack 1 → Categoria1, Categoria2, Categoria3
        pack1.addCategoria(categoria1);
        pack1.addCategoria(categoria2);

        // Pack 2 → Categoria1
        pack2.addCategoria(categoria1);
        pack2.addCategoria(categoria2);

        // Pack 3 → Categoria1
        pack3.addCategoria(categoria1);

        return List.of(
                pack1, pack2, pack3
        );
    }

    public static Optional<Pack> packMock() {
        return Optional.of(Pack.builder()
                .packId(1L)
                .nombre("Pack 1")
                .descripcion("Primer pack de pruebas")
                .precio(10000)
                .stock(50)
                .activo(true)
                .build());
    }

    public static PackCreateRequest newPackMock() {
        return new PackCreateRequest(
                "imagen 1",
                "Pack 1",
                10000,
                50,
                "Primer pack de prueba",
                ETipo.PACK,
                List.of(1L, 2L, 3L),
                List.of(new ProductoCantidadRequest(1L, 5))
        );
    }

    public static List<Categoria> categoriaListMock() {
        // ===== Categorías =====
        Categoria categoria1 = Categoria.builder()
                .categoriaId(1L)
                .nombre("Categoria 1")
                .build();

        Categoria categoria2 = Categoria.builder()
                .categoriaId(2L)
                .nombre("Categoria 2")
                .build();

        Categoria categoria3 = Categoria.builder()
                .categoriaId(3L)
                .nombre("Categoria 3")
                .build();

        return List.of(
                categoria1, categoria2, categoria3
        );
    }

    public static Optional<Producto> productoMock() {
        return Optional.of(Producto.builder()
                .productoId(1L)
                .nombre("Producto 1")
                .descripcion("Primer producto de pruebas")
                .activo(true)
                .precio(2000)
                .stock(500)
                .build());
    }

    // Controller
    public static List<PackResponse> packResponseListMock() {

        PackResponse pack1 = new PackResponse(
                1L,
                "imagen 1",
                "Pack 1",
                10000,
                50,
                "Primer pack de prueba",
                ETipo.PACK,
                List.of(new PackCategoriaResponse(1L, "Categoria 1")),
                List.of(new ProductoCantidadResponse(1L, "Producto 1",5))
        );

        PackResponse pack2 = new PackResponse(
                2L,
                "imagen 2",
                "Pack 2",
                20000,
                30,
                "Segundo pack de prueba",
                ETipo.PACK,
                List.of(new PackCategoriaResponse(2L, "Categoria 2")),
                List.of(new ProductoCantidadResponse(2L, "Producto 2",3))
        );

        return List.of(
                pack1, pack2
        );
    }

    public static MultiValueMap<String, String> packParameters() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // Datos base de Pack
        params.add("imagen", "imagen1.png");
        params.add("nombre", "Pack 1");
        params.add("precio", "10000");
        params.add("stock", "50");
        params.add("descripcion", "Primer pack de prueba");
        params.add("tipo", "PACK");
        // Lista de Categorías
        params.add("categoriaIds", "1");
        params.add("categoriaIds", "2");
        params.add("categoriaIds", "3");
        // Lista de Productos con cantidades
        params.add("productos[0].productoId", "1");
        params.add("productos[0].cantidad", "5");
        params.add("productos[1].productoId", "2");
        params.add("productos[1].cantidad", "3");

        return params;
    }
}
