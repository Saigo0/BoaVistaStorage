package com.ibdev.boavistastorage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "gerente")
public class Gerente extends Funcionario {

    @OneToMany(mappedBy = "gerente")
    private List<Compra> compra;

    public Gerente() {
    }

    public Gerente(String nome, String telefone, String CPF, String endereco, String login, String senha, Compra compra) {
        super(nome, telefone, CPF, endereco, login, senha);
        this.compra = new ArrayList<>();
    }

    public List<Compra> getCompra() {
        return this.compra;
    }

    public void addCompra(Compra compra) {
        if (this.compra != null) {
            this.compra.add(compra);
        } else {
            this.compra = List.of(compra);
        }
    }

    public void removeCompra(Compra compra) {
        if (this.compra != null) {
            this.compra.remove(compra);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Gerente{" +
                "compra=" + compra +
                '}';
    }
}
