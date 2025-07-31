package com.application.historia.repository;

import com.application.historia.entity.Historia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriaRepository extends JpaRepository<Historia, Long> {
}
