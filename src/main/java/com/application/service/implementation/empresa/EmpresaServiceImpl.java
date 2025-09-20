package com.application.service.implementation.empresa;

import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.EmpresaRepository;
import com.application.persistence.repository.UsuarioRepository;
import com.application.service.interfaces.empresa.EmpresaService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
}