package com.application.service.implementation.producto;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.categoria.SubCategoria;
import com.application.persistence.entity.producto.Producto;
import com.application.persistence.entity.producto.enums.ETipo;
import com.application.persistence.repository.CategoriaRepository;
import com.application.persistence.repository.ProductoRepository;
import com.application.persistence.repository.SubCategoriaRepository;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.producto.request.ProductoCreateRequest;
import com.application.presentation.dto.producto.response.ProductoResponse;
import com.application.service.interfaces.ImagenService;
import com.application.service.interfaces.producto.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final SubCategoriaRepository subCategoriaRepository;
    private final ImagenService imagenService;

    /**
     * Obtiene un producto por su ID.
     * Este método es de uso interno para otros métodos del servicio.
     *
     * @param id ID del producto a buscar
     * @return La entidad producto encontrada
     * @throws EntityNotFoundException si la producto no existe
     */
    @Override
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: El producto con id: " + id + " no existe"));
    }

    /**
     * Obtiene un producto como DTO de respuesta por su ID.
     * Este método es para la presentación del producto en la pagina de
     * Producto-Descripción
     *
     * @param id ID del producto a buscar
     * @return DTO con la información del producto, incluyendo categorías.
     */
    @Override
    public ProductoResponse getProductoResponseById(Long id) {
        Producto producto = this.getProductoById(id);
        return this.toResponse(producto);
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
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene todos los productos que están activos (disponibles para la venta).
     * Este método es para la vista del E-commerce
     *
     * @return Lista de DTOs con productos activos
     */
    @Override
    public List<ProductoResponse> getProductosActivos() {
        List<Producto> productos = productoRepository.findByActivoTrue();
        return productos.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene todos los productos que están activos y sean distintos de
     * Etipo.UNIDAD,
     * Este método es para la vista de Pack para listar cajas, pack y combos
     * que estén disponibles para la venta
     *
     * @return Lista de DTOs con productos activos que no sean individuales
     */
    @Override
    public List<ProductoResponse> getPacksActivos() {
        List<Producto> productoList = productoRepository.findByeTipoNotAndActivoTrue(ETipo.UNIDAD);
        return productoList.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene los productos que pertenecen a una categoría específica.
     * Este método sera de uso común para la pagina de Productos, en el filtro
     *
     * @param id ID de la categoría
     * @return Lista de DTOs con productos que pertenecen a la categoría indicada
     */
    @Override
    public List<ProductoResponse> getProductoByCategoriaId(Long id) {
        List<Producto> productos = productoRepository.findByCategorias_CategoriaId(id);
        return productos.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Crea un nuevo producto a partir de un DTO de creación.
     *
     * @param productoRequest DTO con los datos del nuevo producto
     * @return Respuesta con mensaje de confirmación
     */
    @Override
    @Transactional
    public GeneralResponse createProducto(@NotNull ProductoCreateRequest productoRequest) {

        String imagen = this.imagenService.asignarImagen(productoRequest.imagen(), "imagen-producto");

        Producto producto = Producto.builder()
                .codigoProducto(productoRequest.codigoProducto())
                .imagen(imagen)
                .nombre(productoRequest.nombre())
                .marca(productoRequest.marca())
                .pais(productoRequest.pais())
                .eTipo(productoRequest.tipo())
                .precio(productoRequest.precio())
                .stock(productoRequest.stock())
                .descripcion(productoRequest.descripcion())
                .activo(productoRequest.activo())
                .build();

        Long categoriaId = productoRequest.categoriaId();
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La categoria con id: " + categoriaId + " no exite"));
        categoria.addProducto(producto);

        Long subCategoriaId = productoRequest.subCategoriaId();
        SubCategoria subCategoria = subCategoriaRepository.findById(subCategoriaId)
                        .orElseThrow(() -> new EntityNotFoundException("La subcategoria con id: " + subCategoriaId + " no existe"));

        if (!subCategoria.getCategoria().getCategoriaId().equals(categoriaId)) {
            throw new IllegalArgumentException("La subcategoria seleccionada no pertenece a la categoría " + categoria.getNombre());
        }

        subCategoria.addProducto(producto);

        productoRepository.save(producto);

        return new GeneralResponse("Producto creado exitosamente");
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param productoRequest DTO con los datos actualizados
     * @param id              ID del producto a actualizar
     * @return Respuesta con mensaje de confirmación
     * @throws EntityNotFoundException si el producto no existe
     */
    @Override
    @Transactional
    public GeneralResponse updateProducto(@NotNull ProductoCreateRequest productoRequest, Long id) {

        Producto producto = this.getProductoById(id);

        String imagen = this.imagenService.asignarImagen(productoRequest.imagen(), "imagen-producto");

        producto.setCodigoProducto(productoRequest.codigoProducto());
        producto.setImagen(imagen);
        producto.setNombre(productoRequest.nombre());
        producto.setMarca(productoRequest.marca());
        producto.setPais(productoRequest.pais());
        producto.setETipo(productoRequest.tipo());
        producto.setPrecio(productoRequest.precio());
        producto.setStock(productoRequest.stock());
        producto.setDescripcion(productoRequest.descripcion());
        producto.setActivo(productoRequest.activo());

        producto.getCategoria().deleteProducto(producto);
        producto.getSubCategoria().deleteProducto(producto);

        Long categoriaId = productoRequest.categoriaId();
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La categoria con id: " + categoriaId + " no exite"));
        categoria.addProducto(producto);

        Long subCategoriaId = productoRequest.subCategoriaId();
        SubCategoria subCategoria = subCategoriaRepository.findById(subCategoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La subcategoria con id: " + subCategoriaId + " no existe"));

        if (!subCategoria.getCategoria().getCategoriaId().equals(categoriaId)) {
            throw new IllegalArgumentException("La subcategoria seleccionada no pertenece a la categoría " + categoria.getNombre());
        }

        subCategoria.addProducto(producto);

        productoRepository.save(producto);

        return new GeneralResponse("Producto actualizado exitosamente");
    }

    /**
     * Deshabilita un producto.
     * El producto seguirá existiendo en la base de datos, pero no se mostrará en la
     * vista de Productos.
     *
     * @param id ID del producto a deshabilitar
     * @return Respuesta con mensaje de confirmación
     * @throws EntityNotFoundException si el producto no existe
     */
    @Override
    public GeneralResponse disableProducto(Long id) {
        Producto producto = this.getProductoById(id);
        producto.setActivo(false);
        productoRepository.save(producto);

        return new GeneralResponse("Producto deshabilitado exitosamente");
    }

    /**
     * Elimina un producto y rompe sus relaciones con categorías para evitar
     * errores de integridad referencial en la base de datos.
     *
     * @param id ID del producto a eliminar
     * @return Respuesta con mensaje de confirmación
     * @throws EntityNotFoundException si el producto no existe
     */
    @Override
    @Transactional
    public GeneralResponse deleteProducto(Long id) {

        Producto producto = this.getProductoById(id);

        for (Categoria categoria : new HashSet<>(producto.getCategorias())) {
            producto.deleteCategoria(categoria);
        }

        productoRepository.delete(producto);
        return new GeneralResponse("Producto eliminado exitosamente");
    }

    /**
     * Convierte una entidad Producto a su DTO de respuesta, incluyendo
     * categoria y subcategoria.
     * Para uso interno del servicio en los métodos de búsqueda
     *
     * @param producto Entidad producto a convertir
     * @return DTO con la información completa del producto
     */
    @Override
    public ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getProductoId(),
                producto.getCodigoProducto(),
                producto.getImagen(),
                producto.getNombre(),
                producto.getMarca(),
                producto.getPais(),
                producto.getETipo(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getDescripcion(),
                producto.isActivo(),
                producto.getCategoria().getNombre(),
                producto.getSubCategoria().getNombre()
        );
    }
}