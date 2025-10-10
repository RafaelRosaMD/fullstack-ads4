package com.senac.full.application.dto.usuario;

import com.senac.full.domain.entities.Usuario;

public record UsuarioResponseDto (Long id, String nome, String cpf, String senha, String email, String role) {

    public UsuarioResponseDto(Usuario usuario) {
        this(id, nome, cpf, senha, email, "ROLE_USER");
    }

}
