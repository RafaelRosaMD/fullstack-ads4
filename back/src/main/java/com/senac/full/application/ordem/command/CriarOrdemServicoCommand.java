package com.senac.full.application.ordem.command;

public record CriarOrdemServicoCommand(
        String cliente,
        String descricaoDefeito
) { }