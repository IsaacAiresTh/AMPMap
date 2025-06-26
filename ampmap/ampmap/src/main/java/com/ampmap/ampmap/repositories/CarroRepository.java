package com.ampmap.ampmap.repositories;

import com.ampmap.ampmap.model.entities.Cars;
import com.ampmap.ampmap.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarroRepository extends JpaRepository<Cars, UUID> {
    // Método para buscar todos os carros de um usuário específico
    List<Cars> findByUsuario(Usuario usuario);
}