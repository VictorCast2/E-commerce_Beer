package com.application.provider;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.pack.Pack;
import com.application.persistence.entity.pack.PackProducto;
import com.application.persistence.entity.producto.Producto;

import java.util.ArrayList;
import java.util.List;

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
}
