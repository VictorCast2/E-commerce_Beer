package com.application.persistence.repository;

import com.application.persistence.entity.pack.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
    List<Pack> findByActivoTrue();
    List<Pack> findByCategorias_CategoriaId(Long categoriaId);
}