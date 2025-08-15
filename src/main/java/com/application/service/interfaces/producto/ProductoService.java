package com.application.service.interfaces.producto;

import com.application.persistence.entity.producto.Producto;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.producto.request.ProductoCreateResquest;
import com.application.presentation.dto.producto.response.ProductoResponse;

import java.util.List;

public interface ProductoService {
    Producto getProsuctoById(Long id);
    List<ProductoResponse> getProductos();
    List<ProductoResponse> getProductosActivos();

    // CRUD
    GeneralResponse addProducto(ProductoCreateResquest productoResquest);
    GeneralResponse updateProducto(ProductoCreateResquest productoResquest, Long id);
    GeneralResponse disableProducto(Long id);
    GeneralResponse deleteProducto(Long id);
}
