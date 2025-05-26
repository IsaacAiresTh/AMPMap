package com.ampmap.ampmap.model.mappers;

import com.ampmap.ampmap.dtos.EstacaoDTO;
import com.ampmap.ampmap.model.entities.Estacao;

public class EstacaoMapper {

    public static EstacaoDTO convertToDto(Estacao estacao) {
        return new EstacaoDTO(estacao.getConector(), estacao.getPotencia(), estacao.getStatus());
        // Adicione outros campos conforme necessário quando o DTO e a Entidade estiverem completos
    }
}
