package com.senac.full.presentation.ordem;

import com.senac.full.domain.ordem.OrdemServico;
import com.senac.full.domain.ordem.StatusOrdemServico;

public record OrdemServicoResponse(
        Long id,
        String cliente,
        String descricaoDefeito,
        StatusOrdemServico status
) {
    public static OrdemServicoResponse fromDomain(OrdemServico os) {
        return new OrdemServicoResponse(
                os.getId(),
                os.getCliente(),
                os.getDescricaoDefeito(),
                os.getStatus()
        );
    }
}