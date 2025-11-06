package com.senac.full.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.senac.full.dto.UsuarioPrincipalDto;
import com.senac.full.model.Token;
import com.senac.full.model.Usuario;
import com.senac.full.repository.TokenRepository;
import com.senac.full.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}") // minutos
    private int tempoEmMinutos;

    @Value("${jwt.issuer}")
    private String emissor;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String gerarToken(String email) {
        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(usuario.getEmail())
                //.withExpiresAt(gerarDataExpiracao())
                .sign(algorithm);

        // COM ESTADO (opcional): mantém lista de tokens válidos
        tokenRepository.save(new Token(null, token, usuario));

        return token;

    }
//
//    public UsuarioPrincipalDto validarToken(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(secret);
//        JWTVerifier verifier = JWT.require(algorithm)
//                .withIssuer(emissor)      // agora vindo do properties
//                .build();
//
//        var claims = verifier.verify(token).getClaims();
//        var email = claims.get("sub").asString();
//
//        // 🔴 COMENTE este bloco de checagem no banco por enquanto:
//    var tokenResult = tokenRepository.findByToken(token).orElse(null);
//    if (tokenResult == null) {
//        throw new IllegalArgumentException("Token inválido (não encontrado no repositório).");
//    }
//    return tokenResult.getUsuario();
//
//        // ✅ Retorna usuário pelo subject (stateless)
//        return usuarioRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
//    }
//
//    private Instant gerarDataExpiracao() {
//        return LocalDateTime.now()
//                .plusMinutes(tempoEmMinutos)
//                .toInstant(ZoneOffset.of("-03:00"));
//    }
//}

        public UsuarioPrincipalDto validarToken (String token){
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(emissor)
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            List<String> roles = jwt.getClaim("roles").asList(String.class);

            verifier.verify(token);

            var tokenResult = tokenRepository.findByToken(token).orElse(null);

            if (tokenResult == null) {
                throw new IllegalArgumentException("Token invalido!");
            }

            return new UsuarioPrincipalDto(tokenResult.getUsuario());
        }
    }