package com.ibdev.boavistastorage.entity;



import com.sun.istack.NotNull;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "fornecedor")
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFornecedor;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String CNPJ;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String cidade;

    @OneToMany(mappedBy = "fornecedor")
    private List<Fornecimento> fornecimentos;

    @OneToMany(mappedBy = "fornecedor")
    private List<Compra> compras;

    public Fornecedor() {}

    public Fornecedor(String nome, String CNPJ, String telefone, String email, String endereco, String cidade) {
        this.setNome(nome);
        this.setCNPJ(CNPJ);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setEndereco(endereco);
        this.setCidade(cidade);
        this.compras = new ArrayList<Compra>();
        this.fornecimentos = new ArrayList<Fornecimento>();
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<Fornecimento> getFornecimentos() {
        return fornecimentos;
    }

    public void addFornecimento(Fornecimento umFornecimento) {
        this.addFornecimento(umFornecimento);
    }

    public void removeFornecimento(Fornecimento umFornecimento) {
        this.removeFornecimento(umFornecimento);
    }

    public void setFornecimentos(List<Fornecimento> fornecimentos) {
        this.fornecimentos = fornecimentos;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void addCompra(Compra umCompra) {
        this.addCompra(umCompra);
    }

    public void removeCompra(Compra umCompra) {
        this.removeCompra(umCompra);
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "idFornecedor=" + idFornecedor +
                ", nome='" + nome + '\'' +
                ", CNPJ='" + CNPJ + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                ", cidade='" + cidade + '\'' +
                ", fornecimentos=" + fornecimentos +
                ", compras=" + compras +
                '}';
    }
}
