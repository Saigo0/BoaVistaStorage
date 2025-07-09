package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendavel")
public class Vendavel extends Produto {
    @Column(nullable = false)
    private double quantEstoque;

    @Column(nullable = false)
    private double precoVenda;

    @OneToMany(mappedBy = "vendavel")
    private List<Fornecimento> fornecimentos;

    @Enumerated(EnumType.STRING)
    private StatusEstoque statusEstoque;

    public Vendavel() {
    }

    public Vendavel(String nome, double precoCusto, double precoVenda, double quantidade) {
        super(nome, precoCusto);
        this.setPrecoVenda(precoVenda);
        this.quantEstoque = quantidade;
        this.fornecimentos = new ArrayList<Fornecimento>();
    }

    public List<Fornecimento> getFornecimentos() {
        return this.fornecimentos;
    }

    public void addFornecimento(Fornecimento fornecimento) {
        if (this.fornecimentos != null) {
            this.fornecimentos.add(fornecimento);
        }
    }

    public void removeFornecimento(Fornecimento fornecimento) {
        if (this.fornecimentos != null) {
            this.fornecimentos.remove(fornecimento);
        }
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public double getQuantEstoque() {
        return quantEstoque;
    }

    public void setQuantEstoque(double quantEstoque) {
        this.quantEstoque = quantEstoque;
    }

    public StatusEstoque getStatusEstoque() {
        return statusEstoque;
    }

    public void setStatusEstoque(StatusEstoque statusEstoque) {
        this.statusEstoque = statusEstoque;
    }

    @Override
    public String toString() {
        return "Vendavel{" +
                "fornecimentos=" + this.fornecimentos +
                ", precoVenda=" + this.precoVenda +
                ", quantEstoque=" + quantEstoque +
                '}';
    }
}
