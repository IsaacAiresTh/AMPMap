package com.ampmap.ampmap.controllers;

import com.ampmap.ampmap.dtos.CarroCriacaoDTO;
import com.ampmap.ampmap.dtos.CarsDTO;
import com.ampmap.ampmap.services.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carros")
@Tag(name = "Carros")
@SecurityRequirement(name = "bearerAuth") // Exige autenticação para todos os endpoints neste controller
public class CarroController {

    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Adicionar um novo carro ao perfil do usuário logado")
    public ResponseEntity<CarsDTO> adicionarCarro(@Valid @RequestBody CarroCriacaoDTO dto) {
        CarsDTO novoCarro = carroService.adicionarCarro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCarro);
    }

    @GetMapping("/meus-carros")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Listar todos os carros do usuário logado")
    public ResponseEntity<List<CarsDTO>> listarCarros() {
        List<CarsDTO> carros = carroService.listarCarrosDoUsuarioLogado();
        return ResponseEntity.ok(carros);
    }

    @PutMapping("/{carroId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Atualizar um carro do usuário logado")
    public ResponseEntity<CarsDTO> atualizarCarro(@PathVariable UUID carroId, @Valid @RequestBody CarroCriacaoDTO dto) {
        CarsDTO carroAtualizado = carroService.atualizarCarro(carroId, dto);
        return ResponseEntity.ok(carroAtualizado);
    }

    @DeleteMapping("/{carroId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Deletar um carro do usuário logado")
    public ResponseEntity<Void> deletarCarro(@PathVariable UUID carroId) {
        carroService.deletarCarro(carroId);
        return ResponseEntity.noContent().build();
    }
}