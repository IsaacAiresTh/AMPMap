package com.ampmap.ampmap.model.mappers;

import com.ampmap.ampmap.dtos.UsuarioDTO;
import com.ampmap.ampmap.model.entities.Usuario;

public class UsuarioMapper {

    // Este método é usado para converter uma entidade Usuario para UsuarioDTO,
    // por exemplo, ao retornar dados de um usuário.
    // A senha NUNCA deve ser incluída aqui.
    public static UsuarioDTO convertToDto(Usuario usuario) {
        // Se o UsuarioDTO tem campo de senha, ele será null aqui, o que é bom.
        // O ideal é ter um DTO de resposta sem o campo senha.
        // Por ora, vamos assumir que o UsuarioDTO pode ter a senha como null.
        return new UsuarioDTO(
                usuario.getNome(),
                null, // NUNCA retorne a senha, mesmo que codificada
                usuario.getEmail(),
                usuario.getStation_owner()
        );
    }

    // Este método é usado para converter um UsuarioDTO (vindo da requisição) para a entidade Usuario,
    // por exemplo, ao criar um novo usuário. A senha aqui é a senha em texto plano
    // que será codificada pelo UsuarioService.
    public static Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setSenha(usuarioDTO.senha()); // A senha será codificada no serviço
        usuario.setEmail(usuarioDTO.email());
        usuario.setStation_owner(usuarioDTO.station_owner() != null ? usuarioDTO.station_owner() : false);
        // cars são gerenciados separadamente, se necessário
        return usuario;
    }
}