package com.application.pack.repository;

import com.application.pack.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackRepository extends JpaRepository<Pack, Long> {
}
