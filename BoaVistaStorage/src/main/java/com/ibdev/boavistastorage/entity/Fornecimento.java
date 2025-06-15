package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fornecimento")
public class Fornecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFornecimento;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "insumo_id", nullable = true)
    private Insumo insumo;

    @ManyToOne
    @JoinColumn(name = "vendavel_id", nullable = true)
    private Vendavel vendavel;

    public Fornecimento(Fornecedor fornecedor, Vendavel vendavel) {
        this.setFornecedor(fornecedor);
        this.setVendavel(vendavel);
    }

    public Fornecimento(Fornecedor fornecedor, Insumo insumo) {
        this.setFornecedor(fornecedor);
        this.setInsumo(insumo);
    }

    public Fornecimento(){}

    public Long getIdFornecimento() {
        return idFornecimento;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }


    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Vendavel getVendavel() {
        return vendavel;
    }

    public void setVendavel (Vendavel vendavel) {
        this.vendavel = vendavel;
    }



    @Override
    public String toString() {
        return "Fornecimento{" +
                "idFornecimento=" + idFornecimento +
                ", fornecedor=" + fornecedor +
                ", insumo=" + insumo +
                ", vendavel=" + vendavel +
                '}';
    }
}
