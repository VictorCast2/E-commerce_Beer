package com.application.service.interfaces.empresa;

import com.application.presentation.dto.empresa.request.CreacionEmpresaRequest;
import com.application.presentation.dto.empresa.request.EditarEmpresaRequest;
import com.application.presentation.dto.empresa.response.EmpresaResponse;
import javax.validation.Valid;

public interface EmpresaService {
    EmpresaResponse crearEmpresa(@Valid String correo);
    EmpresaResponse actualizarEmpresa(@Valid CreacionEmpresaRequest request);
    EmpresaResponse deleteEmpresa(@Valid EditarEmpresaRequest request);
}