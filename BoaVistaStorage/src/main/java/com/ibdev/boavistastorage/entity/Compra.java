package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    @Column(nullable = false)
    private LocalDateTime dataCompra;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Gerente gerente;

    @OneToMany
    private List<ItemCompra> itensCompra;

    @Column(nullable = false)
    private double valorTotalCompra;

    @ManyToOne
    @JoinColumn(name = "financeiro_id")
    private Financeiro financeiro;

    public Compra() {}

    public Compra(Fornecedor fornecedor, Gerente gerente, List<ItemCompra> itensCompra) {
        this.dataCompra = LocalDateTime.now();
        this.setFornecedor(fornecedor);
        this.setGerente(gerente);
        this.itensCompra = new ArrayList<ItemCompra>();
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    public List<ItemCompra> getItensCompra() {
        return itensCompra;
    }

    public void addItensCompra(ItemCompra itemCompra) {
        this.itensCompra.add(itemCompra);
    }

    public void removeItensCompra(ItemCompra itemCompra) {
        this.itensCompra.remove(itemCompra);
    }

    public void setItensCompra(List<ItemCompra> itensCompra) {
        this.itensCompra = itensCompra;
    }

    public double getValorTotalCompra() {
        return valorTotalCompra;
    }

    public void setValorTotalCompra(double valorTotalCompra) {
        this.valorTotalCompra = valorTotalCompra;
    }

    public Financeiro getFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(Financeiro financeiro) {
        this.financeiro = financeiro;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra=" + this.idCompra +
                ", dataCompra=" + this.dataCompra +
                ", fornecedor=" + this.fornecedor.getIdFornecedor() +
                ", id do gerente=" + this.gerente.getId() +
                ", produtosCompra=" + this.itensCompra +
                ", valorTotalCompra=" + this.valorTotalCompra +
                ", financeiro=" + this.financeiro.getIdFinanceiro() +
                '}';
    }
}
