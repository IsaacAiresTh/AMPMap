package com.ampmap.ampmap.dtos;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record FeedbackDTO(

        @NotBlank(message = "O comentário é obrigatório.")
        @Size(max = 300, message = "O comentário deve ter no máximo 300 caracteres.")
        String comentario,

        @NotNull(message = "A nota é obrigatória.")
        @Min(value = 1, message = "A nota deve ser no mínimo 1.")
        @Max(value = 10, message = "A nota deve ser no máximo 10.")
        Integer nota

) {
}
