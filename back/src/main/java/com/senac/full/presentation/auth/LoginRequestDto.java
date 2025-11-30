package com.senac.full.presentation.auth;

public record LoginRequestDto(
        String email,
        String senha
) { }