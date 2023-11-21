package com.emy.nubeink.model.dto;

import android.util.Log;

public class Usuario {
    private String senha;
    private String email;
    private String nome;
    private double saldo;
    private long id;

    public Usuario(long id, String nome, String email, String senha , double saldo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.saldo = saldo;
    }

    // Construtor
    public Usuario(String senha, String email, String nome, double saldo) {
        this.senha = senha;
        this.email = email;
        this.nome = nome;
        this.saldo = saldo;
    }

    public Usuario() {
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Usuario(String senha, String email, String nome) {
        this.senha = senha;
        this.email = email;
        this.nome = nome;
    }

    public Usuario(String email, String senha) {
        this.senha = senha;
        this.email = email;
    }
    public void debitarSaldo(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            Log.i("Usuario", "Saldo debitado com sucesso. Novo saldo: " + saldo);
        } else {
            Log.e("Usuario", "Saldo insuficiente para debitar ou valor inválido.");
        }
    }

    public void creditarSaldo(double valor) {
        if (valor > 0) {
            saldo += valor;
            Log.i("Usuario", "Saldo creditado com sucesso. Novo saldo: " + saldo);
        } else {
            Log.e("Usuario", "Valor inválido para crédito de saldo.");
        }
    }

    public double getSaldo() {
        return saldo;
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

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
