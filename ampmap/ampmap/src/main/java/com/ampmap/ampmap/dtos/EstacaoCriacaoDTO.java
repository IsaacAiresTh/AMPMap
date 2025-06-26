// Código do EstacaoCriacaoDTO.java atualizado

package com.ampmap.ampmap.dtos;

import com.ampmap.ampmap.enumn.EstacaoStatus;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

// DTO para receber os dados de cadastro de uma nova estação
public record EstacaoCriacaoDTO(
        @NotBlank(message = "O nome da estação é obrigatório.")
        String nome,

        @NotBlank(message = "A descrição da estação é obrigatória.")
        String descricao,

        @NotBlank(message = "O tipo de conector é obrigatório.")
        String conector,

        Double potencia,

        @NotNull(message = "O valor por hora é obrigatório.")
        @DecimalMin(value = "0.01", message = "O valor por hora deve ser maior que zero.")
        @Digits(integer = 6, fraction = 2, message = "O valor por hora deve ter no máximo 2 casas decimais.")
        BigDecimal valorPorHora,

        EstacaoStatus status,

        // --- CAMPOS DE ENDEREÇO ADICIONADOS ---
        @NotBlank(message = "O logradouro (rua, avenida) é obrigatório.")
        String logradouro,

        @NotBlank(message = "O número é obrigatório.")
        String numero,
        
        String bairro,

        @NotBlank(message = "A cidade é obrigatória.")
        String cidade,

        @NotBlank(message = "O estado (UF) é obrigatório.")
        String estado,

        @NotBlank(message = "O CEP é obrigatório.")
        String cep
) {
}