package com.senac.full.application.usuario.command;

public record CriarUsuarioCommand(
        String nome,
        String cpf,
        String email,
        String senha,
        String role
) { }