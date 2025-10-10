package com.senac.full.application.dto.ordem;

import com.senac.full.domain.entities.StatusOrdemServico;

public record OrdemServicoResponseDto(
        Long id,
        String cliente,
        String descricaoDefeito,
        StatusOrdemServico status
) { }