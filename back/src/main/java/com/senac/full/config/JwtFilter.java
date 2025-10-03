package com.senac.full.config;


import com.senac.full.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        try {
            // 1) Preflight CORS
            if ("OPTIONS".equalsIgnoreCase(method)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 2) Rotas públicas
            if (path.equals("/auth/login")
                    || path.startsWith("/auth/")
                    || path.startsWith("/swagger-resources")
                    || path.startsWith("/v3/api-docs")
                    || path.startsWith("/swagger-ui")
                    || path.startsWith("/webjars")
                    || path.startsWith("/") //liberando
                    || path.equals("/swagger-ui.html")
                    || path.equals("/error")) {
                filterChain.doFilter(request, response);
                return;
            }

            // 3) Verifica header Authorization
            String header = request.getHeader("Authorization");
            if (header != null && header.toLowerCase().startsWith("bearer ")) {
                String token = header.substring(7).trim();

                var usuario = tokenService.validarToken(token);

                var autorizacao = new UsernamePasswordAuthenticationToken(
                        usuario.getEmail(),
                        null,
                        usuario.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(autorizacao);

                filterChain.doFilter(request, response);
                return;
            }

            // 4) Sem token
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\":\"Token não informado\"}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\":\"Token inválido ou expirado\"}");
        }
    }
}