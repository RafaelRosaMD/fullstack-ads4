package com.senac.full.application.usuario;

import com.senac.full.application.usuario.command.AtualizarUsuarioCommand;
import com.senac.full.application.usuario.command.CriarUsuarioCommand;
import com.senac.full.domain.usuario.Usuario;
import com.senac.full.domain.usuario.UsuarioRepository;
import com.senac.full.application.exception.BusinessException;
import com.senac.full.application.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GerenciarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public GerenciarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario criar(CriarUsuarioCommand command) {

        usuarioRepository.buscarPorEmail(command.email()).ifPresent(u -> {
            throw new BusinessException("Já existe usuário cadastrado com este e-mail.");
        });

        var usuario = Usuario.novo(
                command.nome(),
                command.cpf(),
                command.email(),
                command.senha(),
                command.role()
        );

        return usuarioRepository.salvar(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarioRepository.listar();
    }

    @Transactional(readOnly = true)
    public Usuario buscar(Long id) {
        return getOrThrow(id);
    }

    @Transactional
    public Usuario atualizar(Long id, AtualizarUsuarioCommand command) {
        var usuario = getOrThrow(id);
        usuario.setNome(command.nome());
        usuario.setCpf(command.cpf());
        usuario.setEmail(command.email());
        usuario.setRole(command.role());
        return usuarioRepository.salvar(usuario);
    }

    private Usuario getOrThrow(Long id) {
        return usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));
    }
}