package com.senac.full.config;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Tag(name = "SecurityConfig", description = "Classe de configuração das rotas da API")
@Configuration
@EnableWebSecurity
public class SecureConfiguration {

    @Autowired
    public JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        //autorizações do swagger
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/**").permitAll()

                        //autorizações das rotas de usuario
                        .requestMatchers(HttpMethod.POST,"/access/login/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/usuario/listarTodos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/usuario/usuario/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/usuario/novo").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/usuario/editar/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuario/excluir/**").hasRole("ADMIN")

                        //autorizações das rotas de filme
                        .requestMatchers(HttpMethod.GET,"/filme/listarFilmes").permitAll()
                        .requestMatchers(HttpMethod.GET,"/filme/buscarFilmeId/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/filme/cadastrarFilme").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/editarFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/alugarFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/devolverFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/desativarFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/ativarFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/filme/excluir/**").hasRole("ADMIN")
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}









//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecureConfiguration {
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//
//
//
//
//
//
//
//
//    @Bean
//    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
//
//        return http.cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth ->
//                        auth
//                                .requestMatchers("/auth/login").permitAll()
//                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                                .requestMatchers("/swagger-resources/**").permitAll()
//                                .requestMatchers("/swagger-ui/**").permitAll()
//                                .requestMatchers("/v3/api-docs/**").permitAll()
//                                //.requestMatchers("/**").permitAll() //liberando
//                                .requestMatchers("/usuarios").hasRole("ADMIN")
//                                .anyRequest().authenticated()
//
//                )
//
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//}


