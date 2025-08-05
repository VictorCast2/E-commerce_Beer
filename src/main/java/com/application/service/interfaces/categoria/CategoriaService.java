package com.application.service.interfaces.categoria;

import com.application.persistence.entity.categoria.Categoria;
import com.application.presentation.dto.request.CategoriaCreateRequest;
import com.application.presentation.dto.response.CategoriaResponse;
import com.application.presentation.dto.response.GeneralResponse;

import java.util.List;

public interface CategoriaService {

    Categoria getCategoriaById(Long id);
    List<CategoriaResponse> getCategorias();
    List<CategoriaResponse> getCategoriasActivas();
    GeneralResponse addCategoria(CategoriaCreateRequest categoriaRequest);
    GeneralResponse updateCategoria(CategoriaCreateRequest categoriaRequest, Long id);
    GeneralResponse disableCategoria(Long id);
    GeneralResponse deleteCategoria(Long id);
}
