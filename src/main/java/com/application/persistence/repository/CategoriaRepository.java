package com.application.persistence.repository;

import com.application.persistence.entity.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByActivoTrue();

    @Query("select count(p) from Categoria c join c.packs p where c.id = :categoriaId")
    long countPacksByCategoriaId(@Param("categoriaId") Long categoriaId);

}