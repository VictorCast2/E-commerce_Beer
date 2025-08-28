package com.application.service.implementation.pack;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.pack.Pack;
import com.application.persistence.entity.pack.PackProducto;
import com.application.persistence.entity.producto.Producto;
import com.application.persistence.repository.CategoriaRepository;
import com.application.persistence.repository.PackRepository;
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

    @Override
    public Pack getPackById(Long id) {
        return packRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: El pack con id: " + id + " no existe"));
    }

    @Override
    public List<PackResponse> getPacks() {
        List<Pack> packs = packRepository.findAll();
        return packs.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<PackResponse> getPacksActivos() {
        List<Pack> packs = packRepository.findByActivoTrue();
        return packs.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<PackResponse> getPacksByCategoriaId(Long id) {
        List<Pack> packs = packRepository.findByCategorias_CategoriaId(id);
        return packs.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public GeneralResponse addPack(PackCreateRequest packRequest) {

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
                throw new IllegalArgumentException("No hay suficiente stock del producto: " + producto.getNombre());
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

    @Override
    public GeneralResponse disablePack(Long id) {
        Pack pack = this.getPackById(id);
        pack.setActivo(false);
        packRepository.save(pack);

        return new GeneralResponse("Producto deshabilitado exitosamente");
    }

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
