package com.E_commerce_Beer.repository;

import com.E_commerce_Beer.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
