package com.application.service.interfaces.producto;

import com.application.persistence.entity.producto.Producto;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.producto.request.ProductoCreateRequest;
import com.application.presentation.dto.producto.response.ProductoResponse;
import java.util.List;

public interface ProductoService {
    // Consulta
    Producto getProductoById(Long id);

    ProductoResponse getProductoResponseById(Long id);

    List<ProductoResponse> getProductos();

    List<ProductoResponse> getProductosActivos();

    List<ProductoResponse> getPacksActivos();

    List<ProductoResponse> getProductoByCategoriaId(Long id);

    // CRUD
    GeneralResponse createProducto(ProductoCreateRequest productoRequest);

    GeneralResponse updateProducto(ProductoCreateRequest productoRequest, Long id);

    GeneralResponse disableProducto(Long id);

    GeneralResponse deleteProducto(Long id);

    // Utils
    ProductoResponse toResponse(Producto producto);
}