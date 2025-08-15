package com.application.service.implementation.producto;

import com.application.persistence.entity.producto.Producto;
import com.application.persistence.repository.ProductoRepository;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.producto.request.ProductoCreateResquest;
import com.application.presentation.dto.producto.response.ProductoResponse;
import com.application.service.interfaces.producto.ProductoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene un producto por su ID.
     * Este método es de uso interno para otros métodos del servicio.
     *
     * @param id ID del producto a buscar
     * @return La entidad producto encontrada
     * @throws NoSuchElementException si la producto no existe
     */
    @Override
    public Producto getProsuctoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Error: El producto con id: " + id + " no existe"));
    }

    /**
     * Obtiene todos los productos registrados (activos e inactivos).
     * Uso común para el panel administrativo.
     *
     * @return Lista de DTOs con datos básicos de cada producto
     */
    @Override
    public List<ProductoResponse> getProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(producto -> new ProductoResponse(
                        producto.getImagen(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.getDescripcion(),
                        producto.getMarca(),
                        producto.getPresentacion()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los productos que están activos (disponibles para la venta).
     *
     * @return Lista de DTOs con datos básicos de productos activos
     */
    @Override
    public List<ProductoResponse> getProductosActivos() {
        List<Producto> productos = productoRepository.findByActivoTrue();
        return productos.stream()
                .map(producto -> new ProductoResponse(
                        producto.getImagen(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.getDescripcion(),
                        producto.getMarca(),
                        producto.getPresentacion()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo producto a partir de un DTO de creación.
     *
     * @param productoResquest DTO con los datos del nuevo producto
     * @return Respuesta con mensaje de confirmación
     */
    @Override
    public GeneralResponse addProducto(@NotNull ProductoCreateResquest productoResquest) {

        Producto producto = Producto.builder()
                .imagen(productoResquest.imagen())
                .nombre(productoResquest.nombre())
                .precio(productoResquest.precio())
                .stock(productoResquest.stock())
                .descripcion(productoResquest.descripcion())
                .marca(productoResquest.marca())
                .presentacion(productoResquest.presentacion())
                .activo(true)
                .build();

        productoRepository.save(producto);

        return new GeneralResponse("Producto creado exitosamente");
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param productoResquest DTO con los datos actualizados
     * @param id ID del producto a actualizar
     * @return Respuesta con mensaje de confirmación
     * @throws NoSuchElementException si el producto no existe
     */
    @Override
    public GeneralResponse updateProducto(ProductoCreateResquest productoResquest, Long id) {

        Producto productoActualizado = this.getProsuctoById(id);

        productoActualizado.setImagen(productoResquest.imagen());
        productoActualizado.setNombre(productoResquest.nombre());
        productoActualizado.setPrecio(productoResquest.precio());
        productoActualizado.setStock(productoResquest.stock());
        productoActualizado.setDescripcion(productoResquest.descripcion());
        productoActualizado.setMarca(productoResquest.marca());
        productoActualizado.setPresentacion(productoResquest.presentacion());

        productoRepository.save(productoActualizado);

        return new GeneralResponse("Producto actualizado Exitosamente");
    }

    /**
     * Deshabilita un producto.
     * El producto seguirá existiendo en la base de datos, pero no se mostrará en la vista de Productos.
     *
     * @param id ID del producto a deshabilitar
     * @return Respuesta con mensaje de confirmación
     * @throws NoSuchElementException si el producto no existe
     */
    @Override
    public GeneralResponse disableProducto(Long id) {
        Producto producto = this.getProsuctoById(id);
        producto.setActivo(false);
        productoRepository.save(producto);

        return new GeneralResponse("Producto deshabilitado exitosamente");
    }

    /**
     * Elimina un producto y rompe sus relaciones con packs para evitar
     * errores de integridad referencial en la base de datos.
     *
     * @param id ID del producto a eliminar
     * @return Respuesta con mensaje de confirmación
     * @throws NoSuchElementException si el producto no existe
     */
    @Override
    @Transactional
    public GeneralResponse deleteProducto(Long id) {

        Producto producto = this.getProsuctoById(id);

        if (!producto.getPackProductos().isEmpty()) {

            producto.getPackProductos().forEach(pp -> pp.getPack().deleteProducto(producto));

        }

        productoRepository.delete(producto);
        return new GeneralResponse("Producto eliminado exitosamente");
    }
}
