package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends Pessoa{

    @OneToMany(mappedBy = "cliente")
    private List<Venda> venda;

    public Cliente(String nome, String cpf, String telefone, String endereco) {
        super(nome,cpf,telefone,endereco);
    }

    public Cliente(){};

    public List<Venda> getVenda() {
        return this.venda;
    }

    public void addVenda(Venda venda) {
        this.venda.add(venda);
    }

    public void removeVenda(Venda venda) {
        this.venda.remove(venda);
    }

    @Override
    public String toString() {
        return super.toString() +
                " Venda: " + this.venda;
    }
}
