package com.ampmap.ampmap.services;

import com.ampmap.ampmap.dtos.FeedbackDTO;
import com.ampmap.ampmap.dtos.FeedbackResponseDTO;
import com.ampmap.ampmap.model.entities.Estacao;
import com.ampmap.ampmap.model.entities.Feedback;
import com.ampmap.ampmap.model.mappers.FeedbackMapper;
import com.ampmap.ampmap.repositories.EstacaoRepository;
import com.ampmap.ampmap.repositories.FeedbackRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final EstacaoRepository estacaoRepository;

    @Transactional
    public FeedbackResponseDTO novoFeedback(UUID estacaoId, FeedbackDTO feedbackDTO) {
        Estacao estacao = estacaoRepository.findById(estacaoId)
                .orElseThrow(() -> new RuntimeException("Estacao nao encontrada!"));

        Feedback feedback = FeedbackMapper.toEntity(feedbackDTO, estacao);
        feedback = feedbackRepository.save(feedback);

        return FeedbackMapper.toDto(feedback);
    }

}
