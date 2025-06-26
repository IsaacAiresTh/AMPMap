package com.ampmap.ampmap.dtos;

import jakarta.validation.constraints.NotBlank;

public record CarroCriacaoDTO(
        @NotBlank(message = "A marca do carro é obrigatória.")
        String marca,

        @NotBlank(message = "O modelo do carro é obrigatório.")
        String modelo,

        @NotBlank(message = "O tipo de conector do carro é obrigatório.")
        String tipo_conector
) {
}