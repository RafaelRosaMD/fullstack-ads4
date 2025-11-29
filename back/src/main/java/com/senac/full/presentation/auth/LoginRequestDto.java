// src/main/java/com/senac/full/presentation/auth/LoginRequestDto.java
package com.senac.full.presentation.auth;

public record LoginRequestDto(
        String email,
        String senha
) { }