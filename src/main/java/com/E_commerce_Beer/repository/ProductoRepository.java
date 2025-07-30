package com.E_commerce_Beer.repository;

import com.E_commerce_Beer.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
