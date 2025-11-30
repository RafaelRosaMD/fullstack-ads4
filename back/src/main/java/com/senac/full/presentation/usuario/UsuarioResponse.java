package com.senac.full.presentation.usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String role
) { }