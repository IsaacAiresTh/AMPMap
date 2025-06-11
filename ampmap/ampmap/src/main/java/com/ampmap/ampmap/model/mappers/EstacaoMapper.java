package com.ampmap.ampmap.model.mappers;

import com.ampmap.ampmap.dtos.EstacaoDTO;
import com.ampmap.ampmap.model.entities.Estacao;

public class EstacaoMapper {

    public static EstacaoDTO convertToDto(Estacao estacao) {
        if (estacao == null) {
            return null;
        }
        return new EstacaoDTO(
                estacao.getId(),
                estacao.getNome(),
                estacao.getDescricao(),
                estacao.getConector(),
                estacao.getPotencia(),
                estacao.getValorPorHora(),
                estacao.getFeedbacks(),
                estacao.getStatus(),
                estacao.getFotosUrls()
        );
    }
}