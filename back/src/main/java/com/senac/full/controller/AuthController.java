package com.senac.full.controller;

import com.senac.full.dto.LoginRequestDto;
import com.senac.full.dto.TokenResponseDto;
import com.senac.full.service.TokenService;
import com.senac.full.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação Controller", description = "Controller responsável pela autenticação")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "login", description = "Método responsável por efetuar o login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto request) {
        // validação das credenciais
        if (!usuarioService.validarSenha(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Gera o JWT usando o e-mail como subject (TokenService.gerarToken(String email))
        String jwt = tokenService.gerarToken(request.email());

        // Responde { "token": "..." }
        return ResponseEntity.ok(new TokenResponseDto(jwt));
    }
}
