package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.repository.ProdutoRepository;

import java.util.List;

public class ProdutoService {

    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> buscarTodosProdutosDisponiveis() {
        try {
            return produtoRepository.findAvailableForMenu();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produtos: " + e.getMessage());
        }
    }

    public Produto findProdutoById(Long id) {
        return produtoRepository.findById(id);
    }
}