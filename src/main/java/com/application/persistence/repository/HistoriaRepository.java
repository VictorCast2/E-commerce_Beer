package com.application.persistence.repository;

import com.application.persistence.entity.historia.Historia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriaRepository extends JpaRepository<Historia, Long> {
}