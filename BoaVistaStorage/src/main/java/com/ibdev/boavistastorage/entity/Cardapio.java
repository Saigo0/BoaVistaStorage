package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cardapio")
public class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCardapio;

    @OneToMany(mappedBy = "cardapio")
    @Column(nullable = false)
    private final List<Produto> produtosDisponiveis;

    public Cardapio() {
        produtosDisponiveis = new ArrayList<Produto>();
    }

    public Long getIdCardapio() {
        return idCardapio;
    }

    public void addProdutosDisponiveis(Produto produto) {
        produtosDisponiveis.add(produto);
    }

    public void removeProdutosDisponiveis(Produto produto) {
        produtosDisponiveis.remove(produto);
    }

    public List<Produto> getProdutosDisponiveis() {
        return produtosDisponiveis;
    }

    @Override
    public String toString() {
        return "id: " + this.idCardapio + "\n Produtos disponíveis" + this.produtosDisponiveis;
    }
}
