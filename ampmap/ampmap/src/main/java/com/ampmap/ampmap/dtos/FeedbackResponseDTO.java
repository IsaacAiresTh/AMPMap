package com.ampmap.ampmap.dtos;

import java.util.UUID;

public record FeedbackResponseDTO(
        UUID id,
        String comentario,
        Integer nota
) {
}
