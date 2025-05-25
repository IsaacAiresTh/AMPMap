package com.ampmap.ampmap.dtos;

import com.ampmap.ampmap.enumn.EstacaoStatus; // Se o status for definido na criação
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
// import java.util.List; // Se fosse enviar URLs de fotos prontas, mas vamos usar MultipartFile no controller

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


        EstacaoStatus status
)


{
    // O campo de fotos será tratado como MultipartFile[] no controller, não diretamente neste DTO.
    // Se você quisesse que o DTO carregasse informações sobre as fotos (ex: metadados),
    // poderia adicionar campos aqui, mas o upload em si é separado.
}