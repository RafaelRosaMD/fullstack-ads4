package com.senac.full.presentation.auth;

public record TokenResponseDto(
        String token,
        Long id,
        String nome,
        String email,
        String role
) {}