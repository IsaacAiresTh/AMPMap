package com.ampmap.ampmap.controllers;

import com.ampmap.ampmap.config.security.JwtUtil;
import com.ampmap.ampmap.services.AppUserDetailsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// DTO para o request de login
record AuthenticationRequest(String email, String senha) {}

// DTO para a response de login
record AuthenticationResponse(String jwt) {}

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    // DENTRO DA CLASSE AuthController
    public AuthController(AuthenticationManager authenticationManager, // <-- NOME CORRIGIDO
                          AppUserDetailsService userDetailsService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.email(), authenticationRequest.senha())
            );
        } catch (Exception e) {
            // Considerar retornar um erro mais específico, como BadCredentialsException
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.email());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}