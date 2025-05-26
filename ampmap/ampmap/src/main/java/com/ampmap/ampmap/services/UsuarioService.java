package com.ampmap.ampmap.services;

import com.ampmap.ampmap.dtos.UsuarioDTO;
import com.ampmap.ampmap.model.entities.Usuario;
import com.ampmap.ampmap.model.mappers.UsuarioMapper;
import com.ampmap.ampmap.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuarioMapp = UsuarioMapper.criarUsuario(usuarioDTO);
        Usuario novoUsuario = usuarioRepository.save(usuarioMapp);
        return UsuarioMapper.convertToDto(novoUsuario);
    }
}
