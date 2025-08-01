package com.application.rol.repository;

import com.application.rol.entity.Rol;
import com.application.rol.enums.ERol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByERol(ERol eRol);
}