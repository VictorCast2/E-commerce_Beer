package com.E_commerce_Beer.repository;

import com.E_commerce_Beer.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
