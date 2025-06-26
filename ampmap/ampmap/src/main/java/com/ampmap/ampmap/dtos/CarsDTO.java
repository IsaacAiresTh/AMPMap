package com.ampmap.ampmap.dtos;


public record CarsDTO(
        String marca,
        String modelo,
        UsuarioDTO donoCarro
) {
}
