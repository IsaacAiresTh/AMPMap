package com.ampmap.ampmap.dtos;

import com.ampmap.ampmap.enumn.EstacaoStatus;

//mudar o tipo dos atributos caso necessario, nn tenho ideia ainda dos tipos
public record EstacaoDTO(
        String conector,
        Double potencia,
        EstacaoStatus status) {
}
