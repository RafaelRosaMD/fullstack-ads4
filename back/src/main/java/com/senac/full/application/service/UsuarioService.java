package com.senac.full.application.service;

import com.senac.full.application.dto.LoginRequestDto;
import com.senac.full.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean validarSenha(LoginRequestDto login){

        return usuarioRepository.existsUsuarioByEmailContainingAndSenha(login.email(), login.senha());

    }

}
