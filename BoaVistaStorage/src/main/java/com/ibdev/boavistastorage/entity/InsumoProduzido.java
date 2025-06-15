package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "insumo_produzido")
public class InsumoProduzido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInsumoProduzido;

    @Column(nullable = false)
    private double quantUtilizada;

    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @ManyToOne
    @JoinColumn(name = "produzido_id")
    private Produzido produzido;

    public InsumoProduzido() {}

    public InsumoProduzido(double quantUtilizada, Insumo insumo, Produzido produzido) {
        this.quantUtilizada = quantUtilizada;
        this.insumo = insumo;
        this.produzido = produzido;
    }

    public double getQuantUtilizada() {
        return quantUtilizada;
    }

    public void setQuantUtilizada(double quantUtilizada) {
        this.quantUtilizada = quantUtilizada;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Produzido getProduzido() {
        return produzido;
    }

    public void setProduzido(Produzido produzido) {
        this.produzido = produzido;
    }

    public Long getIdInsumoProduzido() {
        return idInsumoProduzido;
    }

    @Override
    public String toString() {
        return "InsumoProduzido{" +
                "idInsumoProduzido=" + this.idInsumoProduzido +
                ", quantUtilizada=" + this.quantUtilizada +
                ", insumo=" + this.insumo +
                ", produzido=" + this.produzido +
                '}';
    }
}


