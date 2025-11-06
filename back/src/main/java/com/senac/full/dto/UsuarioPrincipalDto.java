package com.senac.full.dto;

import com.senac.full.model.Usuario;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UsuarioPrincipalDto(Long id, String email, Collection<? extends GrantedAuthority> autorizacao) {
    public UsuarioPrincipalDto(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getAuthorities()
        );
    }
}
