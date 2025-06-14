package com.ibdev.boavistastorage.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class Fornecedor {
    private Long idFornecedor;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private String endereco;
    private String cidade;
    private List<String> produtos = new ArrayList<>();

}
