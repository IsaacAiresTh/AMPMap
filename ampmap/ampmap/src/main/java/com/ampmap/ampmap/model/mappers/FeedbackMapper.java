package com.ampmap.ampmap.model.mappers;

import com.ampmap.ampmap.dtos.FeedbackDTO;
import com.ampmap.ampmap.dtos.FeedbackResponseDTO;
import com.ampmap.ampmap.model.entities.Estacao;
import com.ampmap.ampmap.model.entities.Feedback;

public class FeedbackMapper {

    public static Feedback toEntity(FeedbackDTO dto, Estacao estacao) {
        Feedback feedback = new Feedback();
        feedback.setComentario(dto.comentario());
        feedback.setNota(dto.nota());
        feedback.setEstacao(estacao);
        return feedback;
    }

    public static FeedbackResponseDTO toDto(Feedback feedback) {
        return new FeedbackResponseDTO(
                feedback.getId(),
                feedback.getComentario(),
                feedback.getNota()
        );
    }
}
