package com.ibdev.boavistastorage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendavel")
public class Vendavel extends Produto {

    private double precoVenda;
    private double quantEstoque;

    @OneToOne
    private Fornecimento fornecimento;

    public Vendavel(){

    }

    public Vendavel(String nome, double precoCusto, Fornecimento fornecimento, double precoVenda, double quantEstoque) {
        super(nome, precoCusto);
        this.setFornecimento(fornecimento);
        this.setPrecoVenda(precoVenda);
        this.setQuantEstoque(quantEstoque);
    }

    public Fornecimento getFornecimento() {
        return fornecimento;
    }

    public void setFornecimento(Fornecimento fornecimento) {
        this.fornecimento = fornecimento;
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

    @Override
    public String toString() {
        return "Vendavel{" +
                "fornecimento=" + this.fornecimento +
                ", precoVenda=" + this.precoVenda +
                ", quantEstoque=" + quantEstoque +
                '}';
    }
}
