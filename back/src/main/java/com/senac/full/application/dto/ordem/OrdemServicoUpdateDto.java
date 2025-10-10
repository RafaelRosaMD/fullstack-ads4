package com.senac.full.application.dto.ordem;

import jakarta.validation.constraints.NotBlank;

public record OrdemServicoUpdateDto(
        @NotBlank String cliente,
        @NotBlank String descricaoDefeito
) { }