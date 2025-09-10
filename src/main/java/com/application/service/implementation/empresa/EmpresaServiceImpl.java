package com.application.service.implementation.empresa;

import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.EmpresaRepository;
import com.application.persistence.repository.UsuarioRepository;
import com.application.presentation.dto.empresa.request.CreacionEmpresaRequest;
import com.application.presentation.dto.empresa.request.EditarEmpresaRequest;
import com.application.presentation.dto.empresa.response.EmpresaResponse;
import com.application.service.interfaces.empresa.EmpresaService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    @Override
    public EmpresaResponse crearEmpresa(String correo) {
        Usuario usuarioActualizado  = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("El correo no existe: " + correo));
        return null;
    }

    @Override
    public EmpresaResponse actualizarEmpresa(CreacionEmpresaRequest request) {
        return null;
    }

    @Override
    public EmpresaResponse deleteEmpresa(EditarEmpresaRequest request) {
        return null;
    }

}