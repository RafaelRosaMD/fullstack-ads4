package com.senac.full.presentation.ordem;

import jakarta.validation.constraints.NotBlank;

public record OrdemServicoCreateRequest(
        @NotBlank String cliente,
        @NotBlank String descricaoDefeito
) { }