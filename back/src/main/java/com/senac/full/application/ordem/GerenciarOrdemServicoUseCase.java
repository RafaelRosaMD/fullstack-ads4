package com.senac.full.application.ordem;

import com.senac.full.application.ordem.command.AtualizarOrdemServicoCommand;
import com.senac.full.application.ordem.command.CriarOrdemServicoCommand;
import com.senac.full.domain.ordem.OrdemServico;
import com.senac.full.domain.ordem.OrdemServicoRepository;
import com.senac.full.domain.ordem.StatusOrdemServico;
import com.senac.full.domain.usuario.UsuarioRepository;
import com.senac.full.exception.BusinessException;
import com.senac.full.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GerenciarOrdemServicoUseCase {

    private final OrdemServicoRepository ordemRepository;
    private final UsuarioRepository usuarioRepository;

    public GerenciarOrdemServicoUseCase(OrdemServicoRepository ordemRepository,
                                        UsuarioRepository usuarioRepository) {
        this.ordemRepository = ordemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public OrdemServico criar(CriarOrdemServicoCommand command) {

        usuarioRepository.buscarPorId(command.usuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        OrdemServico ordemServico = OrdemServico.nova(
                command.cliente(),
                command.descricaoDefeito(),
                command.usuarioId()
        );

        return ordemRepository.salvar(ordemServico);
    }

    @Transactional(readOnly = true)
    public List<OrdemServico> listarDoUsuario(Long usuarioId) {
        return ordemRepository.listarPorUsuario(usuarioId);
    }

    @Transactional(readOnly = true)
    public OrdemServico buscarDoUsuario(Long id, Long usuarioId) {
        return ordemRepository.buscarPorIdEUsuario(id, usuarioId)
                .orElseThrow(() -> new NotFoundException("Ordem de Serviço não encontrada para este usuário."));
    }

    @Transactional
    public OrdemServico atualizar(Long id, Long usuarioId, AtualizarOrdemServicoCommand command) {
        OrdemServico os = buscarDoUsuario(id, usuarioId);

        if (os.getStatus() == StatusOrdemServico.FINALIZADA) {
            throw new BusinessException("Não é permitido editar uma OS FINALIZADA.");
        }

        os.setCliente(command.cliente());
        os.setDescricaoDefeito(command.descricaoDefeito());

        return ordemRepository.salvar(os);
    }

    @Transactional
    public void excluir(Long id, Long usuarioId) {
        OrdemServico os = buscarDoUsuario(id, usuarioId);

        if (os.getStatus() == StatusOrdemServico.EM_EXECUCAO) {
            throw new BusinessException("Não é permitido excluir OS EM_EXECUCAO.");
        }

        ordemRepository.excluir(os);
    }

    @Transactional
    public OrdemServico iniciarServico(Long id, Long usuarioId) {
        OrdemServico os = buscarDoUsuario(id, usuarioId);

        if (os.getStatus() != StatusOrdemServico.ABERTA) {
            throw new BusinessException("Só é possível iniciar uma OS ABERTA.");
        }

        os.setStatus(StatusOrdemServico.EM_EXECUCAO);
        return ordemRepository.salvar(os);
    }

    @Transactional
    public OrdemServico finalizar(Long id, Long usuarioId) {
        OrdemServico os = buscarDoUsuario(id, usuarioId);

        if (os.getStatus() != StatusOrdemServico.EM_EXECUCAO) {
            throw new BusinessException("Só é possível finalizar uma OS EM_EXECUCAO.");
        }

        os.setStatus(StatusOrdemServico.FINALIZADA);
        return ordemRepository.salvar(os);
    }


}