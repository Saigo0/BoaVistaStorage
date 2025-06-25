package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany
    private List<ItemPedido> itemPedido;

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

    public List<ItemPedido> getItemPedido() {
        return itemPedido;
    }

    public void addItemPedido(ItemPedido item) {
        if (this.itemPedido == null) {
            this.itemPedido = new java.util.ArrayList<>();
        }
        this.itemPedido.add(item);
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
