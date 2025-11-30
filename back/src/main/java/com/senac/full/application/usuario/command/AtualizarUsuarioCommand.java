package com.senac.full.application.usuario.command;

public record AtualizarUsuarioCommand(
        String nome,
        String cpf,
        String email,
        String role
) { }