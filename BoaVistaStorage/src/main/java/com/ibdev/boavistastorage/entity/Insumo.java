package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "insumo")
public class Insumo extends Produto {

    @OneToMany(mappedBy = "insumo")
    private List<Fornecimento> fornecimentos;

    @Column(nullable = false)
    private String unidadeDeMedida;

    @Column(nullable = false)
    private double quantidadeEstoque;

    @Enumerated(EnumType.STRING)
    private StatusEstoque statusEstoque;

    @OneToMany(mappedBy = "insumo")
    @Column(nullable = false)
    private List<InsumoProduzido> insumosProduzidos;

    public Insumo(String nome, double precoCusto, String unidadeDeMedida, double quantidadeEstoque){
        super(nome, precoCusto);
        this.setUnidadeDeMedida(unidadeDeMedida);
        this.setQuantidadeEstoque(quantidadeEstoque);
        this.insumosProduzidos = new ArrayList<InsumoProduzido>();
        this.fornecimentos = new ArrayList<Fornecimento>();
    }

    public Insumo(){

    }





    public String getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(String unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public double getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(double quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public StatusEstoque getStatusEstoque() {
        return statusEstoque;
    }

    public void setStatusEstoque(StatusEstoque statusEstoque) {
        this.statusEstoque = statusEstoque;
    }

    public List<InsumoProduzido> getInsumosProduzidos() {
        return this.insumosProduzidos;
    }

    public void addInsumosProduzidos(InsumoProduzido insumoProduzido) {
        this.insumosProduzidos.add(insumoProduzido);
    }

    public void removeInsumosProduzidos(InsumoProduzido insumoProduzido) {
        this.insumosProduzidos.remove(insumoProduzido);
    }

    public boolean setInsumosProduzidos(List<InsumoProduzido> insumosProduzidos) {
        if (insumosProduzidos == null || insumosProduzidos.isEmpty()) {
            return false;
        }
        this.insumosProduzidos = insumosProduzidos;
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "Insumo{" +
                "fornecimentos=" + this.fornecimentos +
                ", unidadeDeMedida='" + this.unidadeDeMedida + '\'' +
                ", quantidadeEstoque=" + this.quantidadeEstoque +
                ", statusEstoque=" + this.statusEstoque +
                '}';
    }
}
