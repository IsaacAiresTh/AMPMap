package com.ampmap.ampmap.dtos;

import com.ampmap.ampmap.model.entities.Usuario;

public record CarsDTO(
        String marca,
        String modelo,
        UsuarioDTO donoCarro
) {
}
