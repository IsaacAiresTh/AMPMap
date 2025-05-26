package com.ampmap.ampmap.controllers;

import com.ampmap.ampmap.dtos.UsuarioDTO;
import com.ampmap.ampmap.model.entities.Usuario;
import com.ampmap.ampmap.repositories.UsuarioRepository;
import com.ampmap.ampmap.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Obter todos os usuarios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario(s) encontrado(s) com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuario foi encontrado!")
    })
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar novo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso!")
    })
    public ResponseEntity<UsuarioDTO> CreateUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        UsuarioDTO usuario = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }


}
