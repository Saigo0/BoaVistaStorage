package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Fornecedor;
import com.ibdev.boavistastorage.entity.Gerente;
import com.ibdev.boavistastorage.repository.CompraRepository;
import com.ibdev.boavistastorage.entity.Compra;

public class CompraService {
    private CompraRepository compraRepository;
    
    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

//    public boolean salvarCompra(Fornecedor fornecedor, Gerente gerente){
//        if(fornecedor == null || gerente == null) {
//            throw new IllegalArgumentException("Fornecedor ou gerente não pode ser nulo.");
//        }
//
//
//    }
}
