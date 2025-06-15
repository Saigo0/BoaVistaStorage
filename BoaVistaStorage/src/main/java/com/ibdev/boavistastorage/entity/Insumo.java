package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "insumo")
public class Insumo extends Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Compra compra;

    @Column(nullable = false)
    private String unidadeDeMedida;

    @Column(nullable = false)
    private double quantidadeEstoque;

    @Enumerated(EnumType.STRING)
    private StatusEstoque statusEstoque;

    @OneToMany(mappedBy = "insumo")
    @Column(nullable = false)
    private List<InsumoProduzido> insumosProduzidos;

    public Insumo(String nome, double precoCusto, String unidadeDeMedida, double quantidadeEstoque, StatusEstoque status){
        super(nome, precoCusto);
        this.setUnidadeDeMedida(unidadeDeMedida);
        this.setQuantidadeEstoque(quantidadeEstoque);
        this.setStatusEstoque(status);
    }

    public Insumo(){

    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
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
        return insumosProduzidos;
    }

    public void addInsumosProduzidos(InsumoProduzido insumoProduzido) {
        this.insumosProduzidos.add(insumoProduzido);
    }

    public void removeInsumosProduzidos(InsumoProduzido insumoProduzido) {
        this.insumosProduzidos.remove(insumoProduzido);
    }

    @Override
    public String toString() {
        return "Insumo{" +
                "compra=" + this.compra +
                ", unidadeDeMedida='" + this.unidadeDeMedida + '\'' +
                ", quantidadeEstoque=" + this.quantidadeEstoque +
                ", statusEstoque=" + this.statusEstoque +
                ", insumosProduzidos=" + this.insumosProduzidos +
                '}';
    }
}
