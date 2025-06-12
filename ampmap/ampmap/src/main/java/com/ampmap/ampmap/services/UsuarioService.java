package com.ampmap.ampmap.services;

import com.ampmap.ampmap.dtos.UsuarioDTO;
import com.ampmap.ampmap.model.entities.Usuario;
import com.ampmap.ampmap.model.mappers.UsuarioMapper;
import com.ampmap.ampmap.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Injetar PasswordEncoder

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) { // Modificar construtor
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder; // Atribuir
    }

    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioMapper::convertToDto) // Certifique-se que o DTO não expõe a senha
                .collect(Collectors.toList());
    }

    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        // Codificar a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha()));
        usuario.setEmail(usuarioDTO.email());
        // Certifique-se de que o station_owner não seja nulo se o DTO permitir.
        // A entidade Usuario agora tem um default de false se não for fornecido,
        // mas é bom garantir a consistência.
        usuario.setStation_owner(usuarioDTO.station_owner() != null ? usuarioDTO.station_owner() : false);

        Usuario novoUsuario = usuarioRepository.save(usuario);
        // O UsuarioMapper.convertToDto não deve incluir a senha. Se incluir, ajuste-o.
        return UsuarioMapper.convertToDto(novoUsuario);
    }
}