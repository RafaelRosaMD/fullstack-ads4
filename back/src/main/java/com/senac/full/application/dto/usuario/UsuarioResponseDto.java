package com.senac.full.application.dto.usuario;

public record UsuarioResponseDto (Long id, String nome, String cpf, String senha, String email, String role) {

}
