package com.senac.full.presentation.usuario;

import com.senac.full.domain.usuario.Usuario;

public class UsuarioMapper {

    public static UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(
                u.getId(),
                u.getNome(),
                u.getCpf(),
                u.getEmail(),
                u.getRole()
        );
    }
}