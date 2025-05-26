package com.ampmap.ampmap.model.mappers;

import com.ampmap.ampmap.dtos.UsuarioDTO;
import com.ampmap.ampmap.model.entities.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO convertToDto(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(), usuario.getSenha(), usuario.getEmail(), usuario.getStation_owner());
    }

    public static Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setSenha(usuarioDTO.senha()); //Proteger essa senha dps
        usuario.setEmail(usuarioDTO.email());
        usuario.setStation_owner(usuarioDTO.station_owner() != null ? usuarioDTO.station_owner() : null);
        return usuario;
    }
}
