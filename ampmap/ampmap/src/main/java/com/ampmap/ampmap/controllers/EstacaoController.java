package com.ampmap.ampmap.controllers;

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

@RestController
@RequestMapping("/estacoes")
@Tag(name = "Estacoes")
public class EstacaoController {

    private final EstacaoService estacaoService;

    public EstacaoController(EstacaoService estacaoService) {
        this.estacaoService = estacaoService;
    }

    //tem que mudar isso aqui, fzr mapeamento pra entidade DTO
    @GetMapping
    @Operation(summary = "Obter uma estacao passando parametros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estacao encontrada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Not Found!")
    })
    public ResponseEntity<List<Estacao>> obterEstacaoPorFiltros(
            @RequestParam(value = "conector", required = false) String conector,
            @RequestParam(value = "potencia", required = false) String potencia,
            @RequestParam(value = "status", required = false) String status)
    {
       List<Estacao> pesquisaPorFiltros = estacaoService.obterEstacaoPorFiltros(conector, potencia, status);
       return ResponseEntity.ok(pesquisaPorFiltros);
    }
}
