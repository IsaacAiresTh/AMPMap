package com.ampmap.ampmap.controllers;

import com.ampmap.ampmap.dtos.FeedbackDTO;
import com.ampmap.ampmap.dtos.FeedbackResponseDTO;
import com.ampmap.ampmap.services.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/estacao/{estacaoId}")
    public ResponseEntity<FeedbackResponseDTO> criarFeedback(@PathVariable UUID estacaoId, @Valid @RequestBody FeedbackDTO feedbackDTO) {
        FeedbackResponseDTO novoFeedback = feedbackService.novoFeedback(estacaoId, feedbackDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFeedback);
    }
}
