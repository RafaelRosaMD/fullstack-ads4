package com.senac.full.infra.ordem.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataOrdemServicoRepository
        extends JpaRepository<OrdemServicoEntity, Long> {

    // Todas as OS de um usuário
    List<OrdemServicoEntity> findByUsuario_Id(Long usuarioId);

    // OS específica de um usuário
    Optional<OrdemServicoEntity> findByIdAndUsuario_Id(Long id, Long usuarioId);
}