package com.application.service.interfaces.empresa;

import com.application.configuration.Custom.CustomUserPrincipal;
import com.application.presentation.dto.empresa.request.CreateEmpresaRequest;
import com.application.presentation.dto.general.response.GeneralResponse;

public interface EmpresaService {

    GeneralResponse createEmpresa(CustomUserPrincipal principal, CreateEmpresaRequest empresaRequest);
}