package com.api.fibbo.poltrona.repository;

import com.api.fibbo.poltrona.model.Poltrona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoltronaRepository extends JpaRepository<Poltrona, String> {
}