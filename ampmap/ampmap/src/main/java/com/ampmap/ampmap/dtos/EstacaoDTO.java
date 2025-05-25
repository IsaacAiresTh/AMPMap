package com.ampmap.ampmap.dtos;

import com.ampmap.ampmap.enumn.EstacaoStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record EstacaoDTO(
        UUID id,
        String nome,
        String descricao,
        String conector,
        Double potencia,
        BigDecimal valorPorHora, // Adicionado
        EstacaoStatus status,
        List<String> fotosUrls // Adicionado

) {
}