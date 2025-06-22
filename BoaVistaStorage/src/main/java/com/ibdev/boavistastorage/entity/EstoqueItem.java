package com.ibdev.boavistastorage.entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EstoqueItem {
    private final SimpleLongProperty id;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty quantidade;
    private final SimpleDoubleProperty precoCusto;
    private final SimpleObjectProperty<StatusEstoque> statusEstoque;
    private final Produto produtoOriginal;

    public EstoqueItem(Produto produto) {
        this.id = new SimpleLongProperty(produto.getId());
        this.nome = new SimpleStringProperty(produto.getNome());
        this.precoCusto = new SimpleDoubleProperty(produto.getPrecoCusto());
        this.produtoOriginal = produto;

        if (produto instanceof Vendavel) {
            Vendavel vendavel = (Vendavel) produto;
            this.quantidade = new SimpleStringProperty(String.format("%.2f", vendavel.getQuantEstoque()));
            this.statusEstoque = new SimpleObjectProperty<>(vendavel.getStatusEstoque());
        } else if (produto instanceof Insumo) {
            Insumo insumo = (Insumo) produto;
            this.quantidade = new SimpleStringProperty(String.format("%.2f %s", insumo.getQuantidadeEstoque(), insumo.getUnidadeDeMedida()));
            this.statusEstoque = new SimpleObjectProperty<>(insumo.getStatusEstoque());
        } else {
            this.quantidade = new SimpleStringProperty("N/A");
            this.statusEstoque = new SimpleObjectProperty<>(null);
        }
    }

    public Long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public String getQuantidade() {
        return quantidade.get();
    }

    public SimpleStringProperty quantidadeProperty() {
        return quantidade;
    }

    public double getPrecoCusto() {
        return precoCusto.get();
    }

    public SimpleDoubleProperty precoCustoProperty() {
        return precoCusto;
    }

    public StatusEstoque getStatusEstoque() {
        return statusEstoque.get();
    }

    public SimpleObjectProperty<StatusEstoque> statusEstoqueProperty() {
        return statusEstoque;
    }

    public Produto getProdutoOriginal() {
        return produtoOriginal;
    }
}