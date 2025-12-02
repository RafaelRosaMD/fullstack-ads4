package com.senac.full.domain.ordem;

import java.util.List;
import java.util.Optional;

public interface OrdemServicoRepository {

    OrdemServico salvar(OrdemServico ordem);

    Optional<OrdemServico> buscarPorId(Long id);

    List<OrdemServico> listar();

    // ---- Multiusuário ----
    List<OrdemServico> listarPorUsuario(Long usuarioId);

    Optional<OrdemServico> buscarPorIdEUsuario(Long id, Long usuarioId);

    void excluir(OrdemServico ordem);
}