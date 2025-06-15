package com.ibdev.boavistastorage.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String nome;

    @Column(nullable = false)
    protected String telefone;

    @Column(nullable = false)
    protected String CPF;

    @Column(nullable = false)
    protected String endereco;

    public Pessoa(String nome, String telefone, String CPF, String endereco){
        this.setNome(nome);
        this.setTelefone(telefone);
        this.setCPF(CPF);
        this.setEndereco(endereco);
    }

    public Pessoa(){}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean setNome(String nome) {
        if(!nome.isEmpty()){
            this.nome = nome;
            return true;
        } else
            return false;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean setTelefone(String telefone) {
        if(!telefone.isEmpty()){
            this.telefone = telefone;
            return true;
        } else
            return false;
    }

    public String getCPF() {
        return CPF;
    }

    public boolean setCPF(String CPF) {
        if(!CPF.isEmpty()){
            this.CPF = CPF;
            return true;
        } else
            return false;
    }

    public String getEndereco() {
        return endereco;
    }

    public boolean setEndereco(String endereco) {
        if(endereco.isEmpty()){
            this.endereco = endereco;
            return true;
        } else
            return false;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + this.id +
                ", nome='" + this.nome + '\'' +
                ", telefone='" + this.telefone + '\'' +
                ", CPF='" + this.CPF + '\'' +
                ", endereco='" + this.endereco + '\'' +
                '}';
    }
}
