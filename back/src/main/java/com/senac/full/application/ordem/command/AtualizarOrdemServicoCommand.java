package com.senac.full.application.ordem.command;

public record AtualizarOrdemServicoCommand(
        String cliente,
        String descricaoDefeito
) { }