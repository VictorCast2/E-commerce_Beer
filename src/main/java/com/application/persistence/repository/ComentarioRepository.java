package com.application.persistence.repository;

import com.application.persistence.entity.comentario.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByHistoria_HistoriaIdAndActivoTrue(Long historiaId);
}