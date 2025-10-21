package com.application.service.implementation.categoria;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.repository.CategoriaRepository;
import com.application.presentation.dto.categoria.request.CategoriaCreateRequest;
import com.application.presentation.dto.categoria.response.CategoriaResponse;
import com.application.presentation.dto.categoria.response.SubCategoriaResponse;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.service.interfaces.categoria.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Obtiene una categoría por su ID.
     * Este método es de uso interno para otros métodos del servicio.
     *
     * @param id ID de la categoría a buscar
     * @return La entidad categoria encontrada
     * @throws EntityNotFoundException si la categoría no existe
     */
    @Override
    public Categoria getCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La categoria con id: " + id + " no existe o ha sido eliminada"));
    }

    /**
     * Obtiene todas las categorías registradas (activas e inactivas).
     * Uso común para el panel administrativo.
     *
     * @return Lista de DTOs con nombre, descripción y cantidad de packs de las
     *         categorías
     */
    @Override
    public List<CategoriaResponse> getCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene todas las categorías activas.
     * Uso para mostrar categorías disponibles en la sección de Productos.
     *
     * @return Lista de DTOs con las categorías activas
     */
    @Override
    public List<CategoriaResponse> getCategoriasActivas() {
        List<Categoria> categoriasActivas = categoriaRepository.findByActivoTrue();
        return categoriasActivas.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Crea una nueva categoría.
     *
     * @param categoriaRequest DTO con los datos necesarios para crear la categoría
     * @return DTO con mensaje de éxito
     */
    @Override
    public GeneralResponse createCategoria(@NotNull CategoriaCreateRequest categoriaRequest) {

        Categoria categoria = Categoria.builder()
                .nombre(categoriaRequest.nombre())
                .descripcion(categoriaRequest.descripcion())
                .activo(true)
                .build();

        categoriaRepository.save(categoria);

        return new GeneralResponse("categoria creada exitosamente");
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param categoriaRequest DTO con los nuevos datos
     * @param id               ID de la categoría a actualizar
     * @return DTO con mensaje de éxito
     * @throws NoSuchElementException si la categoría no existe
     */
    @Override
    public GeneralResponse updateCategoria(@NotNull CategoriaCreateRequest categoriaRequest, Long id) {

        Categoria categoria = this.getCategoriaById(id);

        categoria.setNombre(categoriaRequest.nombre());
        categoria.setDescripcion(categoriaRequest.descripcion());
        categoriaRepository.save(categoria);

        return new GeneralResponse("categoria actualizada exitosamente");
    }

    /**
     * Deshabilita una categoría.
     * La categoría seguirá existiendo en la base de datos, pero no se mostrará en
     * la vista de Productos.
     *
     * @param id ID de la categoría a deshabilitar
     * @return DTO con mensaje de éxito
     */
    @Override
    public GeneralResponse disableCategoria(Long id) {
        Categoria categoria = this.getCategoriaById(id);
        categoria.setActivo(false);
        categoriaRepository.save(categoria);
        return new GeneralResponse("categoría deshabilitada exitosamente");
    }

    /**
     * Elimina una categoría de la base de datos.
     * Solo es posible si no tiene productos asociados.
     *
     * @param id ID de la categoría a eliminar
     * @return DTO con mensaje de éxito o error
     * @throws IllegalArgumentException si la categoría tiene productos asociados
     */
    @Override
    public GeneralResponse deleteCategoria(Long id) {
        Categoria categoria = this.getCategoriaById(id);

        if (!categoria.getProductos().isEmpty()) {
            throw new IllegalArgumentException("No es posible eliminar una categoria con productos");
        }

        categoriaRepository.delete(categoria);

        return new GeneralResponse("categoria eliminada exitosamente");
    }

    /**
     * Convierte una entidad Categoria a su DTO de respuesta,
     * no incluye la cantidad de productos que tiene
     * Para uso interno del Servicio en los métodos de búsqueda
     *
     * @param categoria Entidad categoria a convertir
     * @return DTO con la información de la categoria
     */
    @Override
    public CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getCategoriaId(),
                categoria.getImagen(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.isActivo(),
                categoriaRepository.countProductosByCategoriaId(categoria.getCategoriaId()),
                categoria.getSubCategorias().stream()
                        .map(subCategoria -> new SubCategoriaResponse(
                                subCategoria.getId(), subCategoria.getNombre()
                        ))
                        .toList()
        );
    }
}
