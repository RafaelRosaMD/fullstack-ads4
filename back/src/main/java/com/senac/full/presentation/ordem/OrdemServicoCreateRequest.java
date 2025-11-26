package com.senac.full.presentation.ordem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrdemServicoCreateRequest(
        @NotBlank String cliente,
        @NotBlank String descricaoDefeito,
        @NotNull Long usuarioId
) { }