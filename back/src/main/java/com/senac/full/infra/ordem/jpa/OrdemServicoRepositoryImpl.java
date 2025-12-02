package com.senac.full.infra.ordem.jpa;

import com.senac.full.domain.ordem.OrdemServico;
import com.senac.full.domain.ordem.OrdemServicoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrdemServicoRepositoryImpl implements OrdemServicoRepository {

    private final SpringDataOrdemServicoRepository jpaRepo;

    public OrdemServicoRepositoryImpl(SpringDataOrdemServicoRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public OrdemServico salvar(OrdemServico ordem) {
        var entity = OrdemServicoEntity.fromDomain(ordem);
        var saved = jpaRepo.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<OrdemServico> buscarPorId(Long id) {
        return jpaRepo.findById(id).map(OrdemServicoEntity::toDomain);
    }

    @Override
    public List<OrdemServico> listar() {
        return jpaRepo.findAll().stream()
                .map(OrdemServicoEntity::toDomain)
                .toList();
    }

    @Override
    public List<OrdemServico> listarPorUsuario(Long usuarioId) {
        return jpaRepo.findByUsuario_Id(usuarioId).stream()
                .map(OrdemServicoEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<OrdemServico> buscarPorIdEUsuario(Long id, Long usuarioId) {
        return jpaRepo.findByIdAndUsuario_Id(id, usuarioId)
                .map(OrdemServicoEntity::toDomain);
    }

    @Override
    public void excluir(OrdemServico ordem) {
        jpaRepo.deleteById(ordem.getId());
    }
}