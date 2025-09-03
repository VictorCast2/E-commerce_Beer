package com.application.service.interfaces.pack;

import com.application.persistence.entity.pack.Pack;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.pack.request.PackCreateRequest;
import com.application.presentation.dto.pack.response.PackResponse;
import java.util.List;

public interface PackService {
    // Consulta
    Pack getPackById(Long id);
    List<PackResponse> getPacks();
    List<PackResponse> getPacksActivos();
    List<PackResponse> getPacksByCategoriaId(Long id);

    // CRUD
    GeneralResponse addPack(PackCreateRequest packRequest);
    GeneralResponse updatePack(PackCreateRequest packRequest, Long id);
    GeneralResponse disablePack(Long id);
    GeneralResponse deletePack(Long id);

    // Utils
    PackResponse toResponse(Pack pack);
}
