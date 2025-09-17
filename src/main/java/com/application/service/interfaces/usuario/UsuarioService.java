package com.application.service.interfaces.usuario;

import com.application.persistence.entity.usuario.Usuario;

public interface UsuarioService {

    Usuario getUsuarioByCorreo(String correo);
}
