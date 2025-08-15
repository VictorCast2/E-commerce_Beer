package com.application;

import com.application.persistence.entity.producto.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoDataProvider {

    public static List<Producto> productoList() {

        Producto producto1 = Producto.builder()
                .nombre("Producto 1")
                .descripcion("Primer producto de prueba")
                .activo(true)
                .build();

        Producto producto2 = Producto.builder()
                .nombre("Producto 2")
                .descripcion("Segundo producto de prueba")
                .activo(false)
                .build();

        Producto producto3 = Producto.builder()
                .nombre("Producto 3")
                .descripcion("Tercer producto de prueba")
                .activo(true)
                .build();

        return new ArrayList<>(List.of(
                producto1, producto2, producto3
        ));
    }
}
