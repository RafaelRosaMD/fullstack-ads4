// src/main/java/com/senac/full/application/usuario/AutenticarUsuarioUseCase.java
package com.senac.full.application.usuario;

import com.senac.full.domain.usuario.Usuario;
import com.senac.full.domain.usuario.UsuarioRepository;
import com.senac.full.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public AutenticarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticar(String email, String senha) {

        var usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        // 🔥 AQUI: senha em texto puro
        if (!usuario.getSenha().equals(senha)) {
            throw new BusinessException("Senha inválida.");
        }

        return usuario;
    }
}