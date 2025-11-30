package com.senac.full.presentation.ordem;

import jakarta.validation.constraints.NotBlank;

public record OrdemServicoUpdateRequest(
        @NotBlank String cliente,
        @NotBlank String descricaoDefeito
) { }