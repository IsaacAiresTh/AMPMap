// Código completo do EstacaoController.java finalizado

package com.ampmap.ampmap.controllers;

import com.ampmap.ampmap.dtos.EstacaoCriacaoDTO;
import com.ampmap.ampmap.dtos.EstacaoDTO;
import com.ampmap.ampmap.enumn.EstacaoStatus;
import com.ampmap.ampmap.services.EstacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/estacoes")
@Tag(name = "Estacoes")
public class EstacaoController {

    private final EstacaoService estacaoService;

    public EstacaoController(EstacaoService estacaoService) {
        this.estacaoService = estacaoService;
    }

    @GetMapping
    @Operation(summary = "Obter estações por filtros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estacao(oes) encontrada(s) com sucesso!"),
    })
    public ResponseEntity<List<EstacaoDTO>> obterEstacaoPorFiltros(
            @RequestParam(value = "conector", required = false) String conector,
            @RequestParam(value = "potencia", required = false) Double potencia,
            @RequestParam(value = "status", required = false) EstacaoStatus status)
    {
        List<EstacaoDTO> estacoesDTO = estacaoService.obterEstacaoPorFiltros(conector, potencia, status);
        return ResponseEntity.ok(estacoesDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma estação por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estação encontrada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Estação não encontrada.")
    })
    @PreAuthorize("isAuthenticated()") // Qualquer usuário autenticado pode ver
    public ResponseEntity<EstacaoDTO> obterEstacaoPorId(@PathVariable UUID id) {
        EstacaoDTO estacaoDTO = estacaoService.obterEstacaoPorId(id);
        return ResponseEntity.ok(estacaoDTO);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(summary = "Cadastrar uma nova estação")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estação cadastrada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PreAuthorize("hasRole('STATION_OWNER')") // Apenas donos de estação podem cadastrar
    public ResponseEntity<EstacaoDTO> criarEstacao(
            @RequestPart("estacao") @Valid EstacaoCriacaoDTO estacaoCriacaoDTO,
            @RequestPart(value = "fotos", required = false) MultipartFile[] fotos) {
        EstacaoDTO novaEstacao = estacaoService.criarEstacao(estacaoCriacaoDTO, fotos);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEstacao);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma estação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estação atualizada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado."),
            @ApiResponse(responseCode = "404", description = "Estação não encontrada.")
    })
    @PreAuthorize("hasRole('STATION_OWNER')")
    public ResponseEntity<EstacaoDTO> atualizarEstacao(@PathVariable UUID id, @Valid @RequestBody EstacaoCriacaoDTO estacaoCriacaoDTO) {
        EstacaoDTO estacaoAtualizada = estacaoService.atualizarEstacao(id, estacaoCriacaoDTO);
        return ResponseEntity.ok(estacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma estação")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estação deletada com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Acesso negado."),
            @ApiResponse(responseCode = "404", description = "Estação não encontrada.")
    })
    @PreAuthorize("hasRole('STATION_OWNER')")
    public ResponseEntity<Void> deletarEstacao(@PathVariable UUID id) {
        estacaoService.deletarEstacao(id);
        return ResponseEntity.noContent().build();
    }
}