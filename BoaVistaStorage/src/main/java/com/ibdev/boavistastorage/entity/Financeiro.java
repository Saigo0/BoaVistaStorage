package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "financeiro")
public class Financeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFinanceiro;

    @OneToMany(mappedBy = "financeiro")
    @Column(nullable = false)
    private final List<Venda> vendas;

    @OneToMany(mappedBy = "financeiro")
    @Column(nullable = false)
    private final List<Compra> compras;

    public Financeiro() {
        this.vendas = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    public Long getIdFinanceiro() {
        return idFinanceiro;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void addVenda(Venda umaVenda){
        this.vendas.add(umaVenda);
    }

    public void removeVenda(Venda umaVenda){
        this.vendas.remove(umaVenda);
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void addCompra(Compra umaCompra){
        this.compras.add(umaCompra);
    }

    public void removeCompra(Compra umaCompra){
        this.compras.remove(umaCompra);
    }

    @Override
    public String toString() {
        return "Financeiro{" +
                "idFinanceiro=" + idFinanceiro +
                ", vendas=" + this.vendas +
                ", compras=" + this.compras +
                '}';
    }
}
