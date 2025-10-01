package com.application.service.implementation.pack;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.producto.Producto;
import com.application.persistence.repository.CategoriaRepository;
import com.application.persistence.repository.ProductoRepository;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.pack.request.PackCreateRequest;
import com.application.presentation.dto.pack.request.ProductoCantidadRequest;
import com.application.presentation.dto.pack.response.PackCategoriaResponse;
import com.application.presentation.dto.pack.response.PackResponse;
import com.application.presentation.dto.pack.response.ProductoCantidadResponse;
import com.application.service.interfaces.pack.PackService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class PackServiceImpl implements PackService {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene un pack por su ID.
     * Este método es de uso interno para otros métodos del servicio.
     *
     * @param id ID del pack a buscar
     * @return La entidad pack encontrada
     * @throws EntityNotFoundException si el pack no existe
     */
    @Override
    public Pack getPackById(Long id) {
        return packRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: El pack con id: " + id + " no existe"));
    }

    /**
     * Obtiene un pack como DTO de respuesta por su ID.
     * Este método es para la presentación del pack en la pagina de Pack-Descripción
     *
     * @param id ID del pack a buscar
     * @return DTO con la información del pack, incluyendo categorías y productos
     */
    @Override
    public PackResponse getPackResponseById(Long id) {
        Pack pack = this.getPackById(id);
        return this.toResponse(pack);
    }

    /**
     * Obtiene todos los packs registrados (activos e inactivos).
     * Uso común para el panel administrativo.
     *
     * @return Lista de DTOs con los datos de cada pack
     */
    @Override
    public List<PackResponse> getPacks() {
        List<Pack> packs = packRepository.findAll();
        return packs.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene únicamente los packs que están activos (disponibles para venta).
     * Este método es para la vista del E-commerce
     *
     * @return Lista de DTOs con packs activos
     */
    @Override
    public List<PackResponse> getPacksActivos() {
        List<Pack> packs = packRepository.findByActivoTrue();
        return packs.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene los packs que pertenecen a una categoría específica.
     * Este método sera de uso común para la pagina de Productos, en el filtro
     *
     * @param id ID de la categoría
     * @return Lista de DTOs con packs que pertenecen a la categoría indicada
     */
    @Override
    public List<PackResponse> getPacksByCategoriaId(Long id) {
        List<Pack> packs = packRepository.findByCategorias_CategoriaId(id);
        return packs.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Crea un nuevo pack, validando el stock de los productos incluidos
     * y descontando las cantidades necesarias.
     *
     * @param packRequest DTO con los datos del pack y sus relaciones
     * @return Mensaje de éxito o error en caso de falta de stock
     */
    @Override
    @Transactional
    public GeneralResponse createPack(PackCreateRequest packRequest) {

        Pack pack = Pack.builder()
                .imagen(packRequest.imagen())
                .nombre(packRequest.nombre())
                .precio(packRequest.precio())
                .stock(packRequest.stock())
                .descripcion(packRequest.descripcion())
                .eTipo(packRequest.tipo())
                .activo(true)
                .build();

        List<Categoria> categoriaList = categoriaRepository.findAllById(packRequest.categoriaIds());
        categoriaList.forEach(pack::addCategoria);

        for (ProductoCantidadRequest productoRequest : packRequest.productos()) {
            Producto producto = productoRepository.findById(productoRequest.productoId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado en el pack"));

            if (producto.getStock() < productoRequest.cantidad() * packRequest.stock()) {
                return new GeneralResponse("No hay suficiente stock del producto: " + producto.getNombre());
            }
        }

        for (ProductoCantidadRequest productoRequest : packRequest.productos()) {
            Producto producto = productoRepository.findById(productoRequest.productoId()).get();

            producto.setStock(producto.getStock() - ( productoRequest.cantidad() * packRequest.stock() ));

            pack.addProducto(producto, productoRequest.cantidad());
        }

        packRepository.save(pack);

        return new GeneralResponse("Pack creado exitosamente");
    }

    /**
     * Actualiza un pack existente, validando nuevamente el stock de los productos,
     * devolviendo primero el stock anterior y aplicando los nuevos valores.
     *
     * @param packRequest DTO con los datos actualizados del pack
     * @param id ID del pack a actualizar
     * @return Mensaje de éxito o error en caso de falta de stock
     */
    @Override
    @Transactional
    public GeneralResponse updatePack(PackCreateRequest packRequest, Long id) {

        Pack packActualizado = this.getPackById(id);

        for (PackProducto pp : packActualizado.getPackProductos()) {
            Producto producto = pp.getProducto();
            producto.setStock(producto.getStock() + ( pp.getCantidad() * packActualizado.getStock() ));
        }

        packActualizado.setImagen(packRequest.imagen());
        packActualizado.setNombre(packRequest.nombre());
        packActualizado.setPrecio(packRequest.precio());
        packActualizado.setStock(packRequest.stock());
        packActualizado.setDescripcion(packRequest.descripcion());
        packActualizado.setETipo(packRequest.tipo());

        for (Categoria categoria : new HashSet<>(packActualizado.getCategorias())) {
            packActualizado.deleteCategoria(categoria);
        }
        List<Categoria> categoriaList = categoriaRepository.findAllById(packRequest.categoriaIds());
        categoriaList.forEach(packActualizado::addCategoria);

        for (PackProducto pp : new HashSet<>(packActualizado.getPackProductos())) {
            packActualizado.deleteProducto(pp.getProducto());
        }

        for (ProductoCantidadRequest productoRequest : packRequest.productos()) {
            Producto producto = productoRepository.findById(productoRequest.productoId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado en el packActualizado"));

            if (producto.getStock() < productoRequest.cantidad() * packRequest.stock()) {
                return new GeneralResponse("No hay suficiente stock del producto: " + producto.getNombre());
            }
        }

        for (ProductoCantidadRequest productoRequest : packRequest.productos()) {
            Producto producto = productoRepository.findById(productoRequest.productoId()).get();

            producto.setStock(producto.getStock() - ( productoRequest.cantidad() * packRequest.stock() ));

            packActualizado.addProducto(producto, productoRequest.cantidad());
        }

        packRepository.save(packActualizado);

        return new GeneralResponse("Pack actualizado exitosamente");
    }

    /**
     * Deshabilita un pack (soft delete).
     * El pack permanece en base de datos pero marcado como inactivo.
     * Útil para no romper relaciones con ventas pasadas y
     * evitar el uso del método deletePack(Long id).
     *
     * @param id ID del pack a deshabilitar
     * @return Mensaje de confirmación
     */
    @Override
    public GeneralResponse disablePack(Long id) {
        Pack pack = this.getPackById(id);
        pack.setActivo(false);
        packRepository.save(pack);

        return new GeneralResponse("Producto deshabilitado exitosamente");
    }

    /**
     * Elimina un pack de forma permanente (hard delete).
     * Se recomienda usarlo solo en casos administrativos, ya que puede
     * afectar la integridad de los datos históricos (ventas).
     *
     * @param id ID del pack a eliminar
     * @return Mensaje de confirmación
     */
    @Override
    @Transactional
    public GeneralResponse deletePack(Long id) {

        Pack pack = this.getPackById(id);

        for (PackProducto pp : pack.getPackProductos()) {
            Producto producto = pp.getProducto();
            producto.setStock(producto.getStock() + ( pp.getCantidad() * pack.getStock() ));
        }

        for (Categoria categoria : new HashSet<>(pack.getCategorias())) {
            pack.deleteCategoria(categoria);
        }

        for (PackProducto pp : new HashSet<>(pack.getPackProductos())) {
            pack.deleteProducto(pp.getProducto());
        }

        packRepository.delete(pack);
        return new GeneralResponse("Pack eliminado exitosamente");
    }

    /**
     * Convierte una entidad Pack a su DTO de respuesta, incluyendo
     * categorías y productos asociados.
     * Para uso interno del servicio en los método de búsqueda
     *
     * @param pack Entidad pack a convertir
     * @return DTO con la información completa del pack
     */
    @Override
    public PackResponse toResponse(Pack pack) {
        return new PackResponse(
                pack.getPackId(),
                pack.getImagen(),
                pack.getNombre(),
                pack.getPrecio(),
                pack.getStock(),
                pack.getDescripcion(),
                pack.getETipo(),
                pack.getCategorias().stream()
                        .map(categoria -> new PackCategoriaResponse(
                                categoria.getCategoriaId(), categoria.getNombre()
                        )).toList(),
                pack.getPackProductos().stream()
                        .map(pp -> new ProductoCantidadResponse(
                                pp.getProducto().getProductoId(), pp.getProducto().getNombre(), pp.getCantidad()
                        )).toList()
        );
    }
}
