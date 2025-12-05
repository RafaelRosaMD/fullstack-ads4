package com.senac.full.infra.ordem.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataOrdemServicoRepository
        extends JpaRepository<OrdemServicoEntity, Long> {

    List<OrdemServicoEntity> findByUsuario_Id(Long usuarioId);

    Optional<OrdemServicoEntity> findByIdAndUsuario_Id(Long id, Long usuarioId);
}