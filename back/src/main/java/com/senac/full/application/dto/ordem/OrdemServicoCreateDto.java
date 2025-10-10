package com.senac.full.application.dto.ordem;

import jakarta.validation.constraints.NotBlank;

public record OrdemServicoCreateDto(
        @NotBlank String cliente,
        @NotBlank String descricaoDefeito
) { }