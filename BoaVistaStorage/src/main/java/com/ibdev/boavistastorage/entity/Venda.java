package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "venda")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenda;

    @OneToOne
    private Pedido pedido;

    @Column(nullable = false)
    private LocalDateTime dataVenda;

    @Column(nullable = false)
    private double valorTotal;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "financeiro_id")
    private Financeiro financeiro;

    public Venda(){}

    public Venda(Pedido pedido, LocalDateTime dataVenda, double valorTotal){
        this.setPedido(pedido);
        this.dataVenda = LocalDateTime.now();
        this.setValorTotal(valorTotal);
    }

    public Long getIdVenda() {
        return idVenda;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Financeiro getFinanceiro() {
        return this.financeiro;
    }

    public void setFinanceiro(Financeiro financeiro) {
        this.financeiro = financeiro;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "idVenda=" + this.idVenda +
                ", pedido=" + this.pedido +
                ", dataVenda=" + this.dataVenda +
                ", valorTotal=" + this.valorTotal +
                ", cliente=" + this.cliente +
                ", financeiro=" + this.financeiro +
                '}';
    }
}
