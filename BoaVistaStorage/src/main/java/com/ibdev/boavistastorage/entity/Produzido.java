package com.ibdev.boavistastorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produzido")
public class Produzido extends Produto {
    @Column(unique = true)
    private String descricao;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private double precoVenda;

    @OneToMany(mappedBy = "produzido")
    @Column(nullable = false)
    private List<InsumoProduzido> insumosProduzidos;

    public Produzido() {}

    //Lembrar de colocar o precoCusto nesse construtor logo após concluir a service dessa classe
    public Produzido(String nome, String descricao, String tipo, double precoVenda) {
        super(nome);
        this.descricao = descricao;
        this.tipo = tipo;
        this.precoVenda = precoVenda;
        this.insumosProduzidos = new ArrayList<InsumoProduzido>();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public void addInsumoProduzido(InsumoProduzido insumoProduzido) {
        this.insumosProduzidos.add(insumoProduzido);
    }

    public void removeInsumoProduzido(InsumoProduzido insumoProduzido) {
        this.insumosProduzidos.remove(insumoProduzido);
    }

    public List<InsumoProduzido> getInsumosProduzidos() {
        return insumosProduzidos;
    }

    public void setInsumosProduzidos(List<InsumoProduzido> insumosProduzidos) {
        this.insumosProduzidos = insumosProduzidos;
    }

    @Override
    public String toString() {
        return super.toString() + "Produzido{" +
                " descricao='" + this.descricao + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precoVenda=" + precoVenda +
                '}';
    }
}
