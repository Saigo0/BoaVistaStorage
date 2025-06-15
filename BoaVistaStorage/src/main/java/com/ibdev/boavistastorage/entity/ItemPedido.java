package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "itemPedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemPedido;

    @Column(nullable = false)
    private int quantidade;

    @OneToOne
    private Produto produto;

    @ManyToOne
    private Pedido pedido;

    public ItemPedido() {}

    public ItemPedido(int quantidade, Produto produto, Pedido pedido) {
        this.setQuantidade(quantidade);
        this.setProduto(produto);
        this.setPedido(pedido);
    }

    public Long getIdItemPedido() {
        return idItemPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItemPedido=" + this.idItemPedido +
                ", quantidade=" + this.quantidade +
                ", produto=" + this.produto +
                ", pedido=" + this.pedido +
                '}';
    }
}