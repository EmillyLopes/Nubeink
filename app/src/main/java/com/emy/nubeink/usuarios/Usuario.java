package com.emy.nubeink.usuarios;

public class Usuario {
    private String senha;
    private String email;
    private String nome;

    // Construtor
    public Usuario(String senha, String email, String nome) {
        this.senha = senha;
        this.email = email;
        this.nome = nome;
    }

    public Usuario(String email, String senha) {
        this.senha = senha;
        this.email = email;
    }

    // Métodos de acesso (getters e setters)
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
