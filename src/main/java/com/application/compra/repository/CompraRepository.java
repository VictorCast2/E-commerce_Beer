package com.application.compra.repository;

import com.application.compra.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
