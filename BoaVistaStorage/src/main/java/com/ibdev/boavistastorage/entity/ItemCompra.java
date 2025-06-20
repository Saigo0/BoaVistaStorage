package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "itemCompra")
public class ItemCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Fornecimento fornecimento;

    private double quantidade;
    private double precoCusto;

    @ManyToOne
    private Compra compra;

    public ItemCompra(Fornecimento fornecimento, double quantidade, double precoCusto) {
        this.setFornecimento(fornecimento);
        this.setQuantidade(quantidade);
        this.setPrecoCusto(precoCusto);
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Compra getCompra(){
        return this.compra;
    }

    public ItemCompra() {}

    public Long getId() {
        return id;
    }

    public Fornecimento getFornecimento() {
        return fornecimento;
    }

    public void setFornecimento(Fornecimento fornecimento) {
        this.fornecimento = fornecimento;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }



    @Override
    public String toString() {
        return "ItemCompra{" +
                "id=" + id +
                ", fornecimento=" + fornecimento +
                ", quantidade=" + quantidade +
                ", precoCusto=" + precoCusto +
                '}';
    }
}
