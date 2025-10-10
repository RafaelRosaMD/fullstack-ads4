package com.senac.full.application.service;

import com.senac.full.application.dto.ordem.OrdemServicoCreateDto;
import com.senac.full.application.dto.ordem.OrdemServicoResponseDto;
import com.senac.full.application.dto.ordem.OrdemServicoUpdateDto;

import com.senac.full.domain.exception.BusinessException;
import com.senac.full.domain.exception.NotFoundException;
import com.senac.full.domain.entities.OrdemServico;
import com.senac.full.domain.entities.StatusOrdemServico;
import com.senac.full.domain.repository.OrdemServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository repo;

    public OrdemServicoService(OrdemServicoRepository repo) {
        this.repo = repo;
    }

    // CRUD
    @Transactional
    public OrdemServicoResponseDto criar(OrdemServicoCreateDto dto) {
        var os = OrdemServico.builder()
                .cliente(dto.cliente())
                .descricaoDefeito(dto.descricaoDefeito())
                .status(StatusOrdemServico.ABERTA)
                .build();
        os = repo.save(os);
        return toResponse(os);
    }

    @Transactional(readOnly = true)
    public List<OrdemServicoResponseDto> listar() {

        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrdemServicoResponseDto buscar(Long id) {

        return toResponse(getOrThrow(id));
    }

    @Transactional
    public OrdemServicoResponseDto atualizar(Long id, OrdemServicoUpdateDto dto) {
        var os = getOrThrow(id);
        if (os.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new BusinessException("Não é permitido editar OS FINALIZADA.");
        }
        os.setCliente(dto.cliente());
        os.setDescricaoDefeito(dto.descricaoDefeito());
        return toResponse(repo.save(os));
    }

    @Transactional
    public void excluir(Long id) {
        var os = getOrThrow(id);
        if (os.getStatus() == StatusOrdemServico.EM_EXECUCAO) {
            throw new BusinessException("Não é permitido excluir OS EM_EXECUCAO.");
        }
        repo.delete(os);
    }

    // Ações de status
    @Transactional
    public OrdemServicoResponseDto iniciarServico(Long id) {
        var os = getOrThrow(id);
        if (os.getStatus() != StatusOrdemServico.ABERTA) {
            throw new BusinessException("Só é possível INICIAR quando a OS está ABERTA.");
        }
        os.setStatus(StatusOrdemServico.EM_EXECUCAO);
        return toResponse(repo.save(os));
    }

    @Transactional
    public OrdemServicoResponseDto finalizar(Long id) {
        var os = getOrThrow(id);
        if (os.getStatus() != StatusOrdemServico.EM_EXECUCAO) {
            throw new BusinessException("Só é possível FINALIZAR quando a OS está EM_EXECUCAO.");
        }
        os.setStatus(StatusOrdemServico.FINALIZADA);
        return toResponse(repo.save(os));
    }

    // helpers
    private OrdemServico getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new NotFoundException("Ordem de Serviço não encontrada: " + id));
    }

    private OrdemServicoResponseDto toResponse(OrdemServico os) {
        return new OrdemServicoResponseDto(
                os.getId(), os.getCliente(), os.getDescricaoDefeito(), os.getStatus()
        );
    }
}