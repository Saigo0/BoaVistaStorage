package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Fornecedor;
import com.ibdev.boavistastorage.entity.Gerente;
import com.ibdev.boavistastorage.entity.ItemCompra;
import com.ibdev.boavistastorage.repository.CompraRepository;
import com.ibdev.boavistastorage.entity.Compra;
import java.util.List;

import java.time.LocalDateTime;

public class CompraService {
    private CompraRepository compraRepository;
    
    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public boolean salvarCompra(Fornecedor fornecedor, Gerente gerente, List<ItemCompra> itensCompra) {
        if(fornecedor == null &&  gerente == null) {
            throw new IllegalArgumentException("Gerente ou fornecedor não podem ser nulos!");
        }
        Compra compra;
        compra = new Compra();
        compra.setDataCompra(LocalDateTime.now());
        compra.setGerente(gerente);
        compra.setFornecedor(fornecedor);
        compra.setItensCompra(itensCompra);
        compra.setValorTotalCompra(calcValorTotal(itensCompra));

        return compraRepository.create(compra);
    }

    public double calcValorTotal(List<ItemCompra> itensCompra) {
        double total = 0;
        for (ItemCompra itemCompra : itensCompra) {
            total += itemCompra.getPrecoCusto() * itemCompra.getQuantidade();
        }
        return total;
    }

    public boolean atualizarCompra(Compra compra) {
        if(compra == null) {
            throw new RuntimeException("Não há como atualizar uma compra nula!");
        }

        return compraRepository.update(compra.getIdCompra(), compra);
    }

    public boolean excluirCompra(Compra compra) {
        if(compra == null) {
            throw new RuntimeException("Não há como excluir uma compra nula!");
        }
        return compraRepository.delete(compra.getIdCompra());
    }
}
