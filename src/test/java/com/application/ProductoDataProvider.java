package com.application;

import com.application.persistence.entity.pack.Pack;
import com.application.persistence.entity.producto.Producto;
import com.application.presentation.dto.producto.request.ProductoCreateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static List<Producto> productoListMock() {

        Producto producto1 = Producto.builder()
                .productoId(1L)
                .nombre("Producto 1")
                .descripcion("Primer producto de pruebas")
                .activo(true)
                .build();

        Producto producto2 = Producto.builder()
                .productoId(2L)
                .nombre("Producto 2")
                .descripcion("Segundo producto de pruebas")
                .activo(false)
                .build();

        Producto producto3 = Producto.builder()
                .productoId(3L)
                .nombre("Producto 3")
                .descripcion("Tercer producto de pruebas")
                .activo(true)
                .build();

        Producto producto4 = Producto.builder()
                .productoId(4L)
                .nombre("Producto 4")
                .descripcion("Cuarto producto de pruebas")
                .activo(false)
                .build();

        Producto producto5 = Producto.builder()
                .productoId(5L)
                .nombre("Producto 5")
                .descripcion("Quinto producto de pruebas")
                .activo(true)
                .build();

        return new ArrayList<>(List.of(
                producto1, producto2, producto3, producto4, producto5
        ));
    }

    public static List<Producto> productoActivoListMock() {
        Producto producto1 = Producto.builder()
                .productoId(1L)
                .nombre("Producto 1")
                .descripcion("Primer producto de pruebas")
                .activo(false)
                .build();

        Producto producto2 = Producto.builder()
                .productoId(2L)
                .nombre("Producto 2")
                .descripcion("Segundo producto de pruebas")
                .activo(false)
                .build();

        Producto producto3 = Producto.builder()
                .productoId(3L)
                .nombre("Producto 3")
                .descripcion("Tercer producto de pruebas")
                .activo(false)
                .build();

        return new ArrayList<>(List.of(
                producto1, producto2, producto3
        ));
    }

    public static Optional<Producto> productoMock() {

        Optional<Producto> producto = Optional.of(Producto.builder()
                .productoId(1L)
                .nombre("Producto 1")
                .descripcion("Primer producto de pruebas")
                .activo(true)
                .precio(2000)
                .stock(50)
                .build());

        Pack pack = Pack.builder()
                .packId(1L)
                .nombre("Pack 1")
                .descripcion("Primer pack de pruebas")
                .build();

        pack.addProducto(producto.get(), 5);

        return producto;
    }

    public static ProductoCreateRequest newProductoMock() {
        return new ProductoCreateRequest("imagen 1", "Producto 1", 1000, 50, "Primer producto de pruebas", "marca 1", "350ml");
    }
}
