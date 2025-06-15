package com.ibdev.boavistastorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Funcionario extends Pessoa {
    @Column(unique = true, nullable = false)
    private String login;
    @Column(unique = true, nullable = false)
    private String senha;

    public Funcionario() {
    }

    public Funcionario(String nome, String telefone, String CPF, String endereco, String login, String senha) {
        super(nome, telefone, CPF, endereco);
        this.setLogin(login);
        this.setSenha(senha);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return super.toString() + "Funcionario{" +
                "login='" + this.login + '\'' +
                ", senha='" + this.senha + '\'' +
                '}';
    }
}