package com.ampmap.ampmap.dtos;

//mudar o tipo dos atributos caso necessario, nn tenho ideia ainda dos tipos
public record EstacaoDTO(
        String conector,
        Double potencia,
        String status) {
}
