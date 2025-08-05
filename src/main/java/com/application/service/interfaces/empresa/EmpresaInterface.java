package com.application.service.interfaces.empresa;

import com.application.presentation.dto.empresa.request.CreacionEmpresaRequest;
import com.application.presentation.dto.empresa.response.EmpresaResponse;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;

@Repository
public interface EmpresaInterface {
    EmpresaResponse deleteEmpresa(String correo);
    EmpresaResponse crearEmpresa(@Valid CreacionEmpresaRequest request);
    EmpresaResponse actualizarEmpresa(String correo, @Valid CreacionEmpresaRequest request);
}