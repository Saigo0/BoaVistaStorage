package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Produzido;
import com.ibdev.boavistastorage.repository.ProduzidoRepository;

public class ProduzidoService {
    private ProduzidoRepository produzidoRepository;

    public ProduzidoService(ProduzidoRepository produzidoRepository) {
        this.produzidoRepository = produzidoRepository;
    }

    public Produzido findById(Long id) {
        try {
            return produzidoRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Produzido: " + e.getMessage());
        }
    }

    public void upadateProduzido(Long id, Produzido produzido) {
        if (produzido == null || produzido.getId() == null) {
            throw new IllegalArgumentException("Produzido inválido ou sem ID.");
        }
        try {
            produzidoRepository.update(id, produzido);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar Produzido: " + e.getMessage());
        }
    }
}
