package com.ampmap.ampmap.controllers;

import com.ampmap.ampmap.dtos.EstacaoDTO; // Importar o DTO
import com.ampmap.ampmap.model.entities.Estacao;
import com.ampmap.ampmap.services.EstacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors; // Para o mapeamento

@RestController
@RequestMapping("/estacoes")
@Tag(name = "Estacoes")
public class EstacaoController {

    private final EstacaoService estacaoService;

    public EstacaoController(EstacaoService estacaoService) {
        this.estacaoService = estacaoService;
    }

    // Método auxiliar para mapear Entidade para DTO
    private EstacaoDTO convertToDto(Estacao estacao) {
        return new EstacaoDTO(estacao.getConector(), estacao.getPotencia(), estacao.getStatus());
        // Adicione outros campos conforme necessário quando o DTO e a Entidade estiverem completos
    }

    @GetMapping
    @Operation(summary = "Obter uma estacao passando parametros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estacao(oes) encontrada(s) com sucesso!"), // Ajuste na descrição
    })
    public ResponseEntity<List<EstacaoDTO>> obterEstacaoPorFiltros(@RequestParam(value = "conector", required = false) String conector, @RequestParam(value = "potencia", required = false) Double potencia, @RequestParam(value = "status", required = false) String status)
    {
        List<Estacao> estacoesEncontradas = estacaoService.obterEstacaoPorFiltros(conector, potencia, status);
        List<EstacaoDTO> estacoesDTO = estacoesEncontradas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(estacoesDTO);
    }
}
    