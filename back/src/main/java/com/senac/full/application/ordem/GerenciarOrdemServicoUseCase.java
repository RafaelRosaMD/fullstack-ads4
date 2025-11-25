package com.senac.full.application.ordem;

import com.senac.full.application.ordem.command.AtualizarOrdemServicoCommand;
import com.senac.full.application.ordem.command.CriarOrdemServicoCommand;
import com.senac.full.domain.ordem.OrdemServico;
import com.senac.full.domain.ordem.OrdemServicoRepository;
import com.senac.full.domain.ordem.StatusOrdemServico;
import com.senac.full.exception.BusinessException;
import com.senac.full.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // aqui eu deixei Spring, pra ficar mais fácil integrar
public class GerenciarOrdemServicoUseCase {

    private final OrdemServicoRepository repo;

    public GerenciarOrdemServicoUseCase(OrdemServicoRepository repo) {
        this.repo = repo;
    }

    // CRUD
    @Transactional
    public OrdemServico criar(CriarOrdemServicoCommand command) {
        var os = OrdemServico.nova(command.cliente(), command.descricaoDefeito());
        return repo.salvar(os);
    }

    @Transactional(readOnly = true)
    public List<OrdemServico> listar() {
        return repo.listar();
    }

    @Transactional(readOnly = true)
    public OrdemServico buscar(Long id) {
        return getOrThrow(id);
    }

    @Transactional
    public OrdemServico atualizar(Long id, AtualizarOrdemServicoCommand command) {
        var os = getOrThrow(id);
        if (os.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new BusinessException("Não é permitido editar OS FINALIZADA.");
        }
        os.setCliente(command.cliente());
        os.setDescricaoDefeito(command.descricaoDefeito());
        return repo.salvar(os);
    }

    @Transactional
    public void excluir(Long id) {
        var os = getOrThrow(id);
        if (os.getStatus() == StatusOrdemServico.EM_EXECUCAO) {
            throw new BusinessException("Não é permitido excluir OS EM_EXECUCAO.");
        }
        repo.excluir(os);
    }

    // Ações de status
    @Transactional
    public OrdemServico iniciarServico(Long id) {
        var os = getOrThrow(id);
        if (os.getStatus() != StatusOrdemServico.ABERTA) {
            throw new BusinessException("Só é possível INICIAR quando a OS está ABERTA.");
        }
        os.setStatus(StatusOrdemServico.EM_EXECUCAO);
        return repo.salvar(os);
    }

    @Transactional
    public OrdemServico finalizar(Long id) {
        var os = getOrThrow(id);
        if (os.getStatus() != StatusOrdemServico.EM_EXECUCAO) {
            throw new BusinessException("Só é possível FINALIZAR quando a OS está EM_EXECUCAO.");
        }
        os.setStatus(StatusOrdemServico.FINALIZADA);
        return repo.salvar(os);
    }

    // helper
    private OrdemServico getOrThrow(Long id) {
        return repo.buscarPorId(id).orElseThrow(() ->
                new NotFoundException("Ordem de Serviço não encontrada: " + id));
    }
}