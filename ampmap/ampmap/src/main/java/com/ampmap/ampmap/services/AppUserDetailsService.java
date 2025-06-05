package com.ampmap.ampmap.services;

import com.ampmap.ampmap.model.entities.Usuario;
import com.ampmap.ampmap.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AppUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Supondo que seu UsuarioRepository tenha um método para buscar por email
        // Se não tiver, você precisará adicionar:
        // Optional<Usuario> findByEmail(String email);
        return usuarioRepository.findByEmail(email) // Você precisará criar este método no UsuarioRepository
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }
}