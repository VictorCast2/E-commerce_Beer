package com.application.comentario.repository;

import com.application.comentario.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
