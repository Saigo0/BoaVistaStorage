package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @OneToMany
    private List<ItemPedido> itensList;

    @OneToOne
    private Venda venda;

    public Pedido(){
    }

    public Pedido(LocalDateTime dataPedido) {
        this.dataPedido = LocalDateTime.now();
        this.itensList = new ArrayList<>();
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public List<ItemPedido> getItensList() {
        return this.itensList;
    }

    public void addItensList(ItemPedido itemPedido) {
        this.itensList.add(itemPedido);
    }

    public void removeItensList(ItemPedido itemPedido) {
        this.itensList.remove(itemPedido);
    }

    public void setVenda(Venda venda) {this.venda = venda; }

    public Venda getVenda() { return this.venda; }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + this.idPedido +
                ", dataPedido=" + this.dataPedido +
                ", itensList=" + this.itensList +
                ", venda=" + this.venda +
                '}';
    }
}


