package com.senac.full.presentation.auth;

import com.senac.full.application.usuario.AutenticarUsuarioUseCase;
import com.senac.full.infra.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final JwtService jwtService;

    public AuthController(AutenticarUsuarioUseCase autenticarUsuarioUseCase,
                          JwtService jwtService) {
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(
            @RequestBody @Valid LoginRequestDto request) {

        var usuario = autenticarUsuarioUseCase.autenticar(
                request.email(),
                request.senha()
        );

        String token = jwtService.generateToken(usuario);

        var response = new TokenResponseDto(
                token,
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole() // se for enum
        );

        return ResponseEntity.ok(response);
    }
}