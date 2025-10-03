package com.senac.full.dto.ordem;

import com.senac.full.model.StatusOrdemServico;

public record OrdemServicoResponseDto(
        Long id,
        String cliente,
        String descricaoDefeito,
        StatusOrdemServico status
) { }