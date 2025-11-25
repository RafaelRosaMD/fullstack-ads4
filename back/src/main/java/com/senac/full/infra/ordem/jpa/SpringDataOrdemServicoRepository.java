package com.senac.full.infra.ordem.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrdemServicoRepository
        extends JpaRepository<OrdemServicoEntity, Long> {
}
