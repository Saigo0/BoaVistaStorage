package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private double precoCusto;

    @ManyToOne
    @JoinColumn(name = "cardapio_id")
    private Cardapio cardapio;

    @OneToOne
    private ItemPedido itemPedido;

    public Produto() {}

    public Produto(String nome, double precoCusto){
        this.nome = nome;
        this.precoCusto = precoCusto;
    }

    public Produto(String nome){
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + this.id +
                ", nome='" + this.nome + '\'' +
                ", precoCusto=" + this.precoCusto +
                ", itemPedido=" + this.itemPedido +
                '}';
    }
}
