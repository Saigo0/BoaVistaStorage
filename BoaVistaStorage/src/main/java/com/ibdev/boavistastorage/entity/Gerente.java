package com.ibdev.boavistastorage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "gerente")
public class Gerente extends Funcionario {
    private Compra compra;

    public Gerente() {
    }

    public Gerente(String nome, String telefone, String CPF, String endereco, String login, String senha, Compra compra) {
        super(nome, telefone, CPF, endereco, login, senha);
        this.setCompra(compra);
    }

    public Compra getCompra() {
        return this.compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public String toString() {
        return super.toString() + "Gerente{" +
                "compra=" + compra +
                '}';
    }
}
