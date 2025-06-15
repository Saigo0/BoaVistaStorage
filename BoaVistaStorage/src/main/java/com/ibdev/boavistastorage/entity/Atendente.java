package com.ibdev.boavistastorage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "atendente")
public class Atendente extends Funcionario {

    public Atendente(String nome, String telefone, String CPF, String endereco, String login, String senha) {
        super(nome, telefone, CPF, endereco, login, senha);
    }

    public Atendente(){}

    @Override
    public String toString() {
        return super.toString();
    }
}
