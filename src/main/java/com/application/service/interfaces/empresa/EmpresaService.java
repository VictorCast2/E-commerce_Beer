package com.application.service.interfaces.empresa;

import com.application.configuration.custom.CustomUserPrincipal;
import com.application.presentation.dto.empresa.request.CreateEmpresaRequest;
import com.application.presentation.dto.empresa.request.UpdateEmpresaRequest;
import com.application.presentation.dto.general.response.GeneralResponse;

public interface EmpresaService {

    GeneralResponse createEmpresa(CustomUserPrincipal principal, CreateEmpresaRequest empresaRequest);
    GeneralResponse updateEmpresa(CustomUserPrincipal principal, UpdateEmpresaRequest empresaRequest);
}