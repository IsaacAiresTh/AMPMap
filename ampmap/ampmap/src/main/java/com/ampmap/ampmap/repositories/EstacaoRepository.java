package com.ampmap.ampmap.repositories;

import com.ampmap.ampmap.model.entities.Estacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstacaoRepository extends JpaRepository<Estacao, UUID> {
}