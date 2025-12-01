package com.senac.full.domain.usuario;

public class Usuario {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String role;  // "ROLE_USER", "ROLE_ADMIN"

    public Usuario(Long id, String nome, String cpf, String email, String senha, String role) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public static Usuario novo(String nome, String cpf, String email, String senha, String role) {
        return new Usuario(null, nome, cpf, email, senha, role);
    }

    // getters (e setters se você quiser mutabilidade)
    public Long getId() { return id; }

    public String getNome() { return nome; }

    public String getCpf() { return cpf; }

    public String getEmail() { return email; }

    public String getSenha() { return senha; }

    public String getRole() { return role; }

    public void setNome(String nome) { this.nome = nome; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public void setEmail(String email) { this.email = email; }

    public void setSenha(String senha) { this.senha = senha; }

    public void setRole(String role) { this.role = role; }
}