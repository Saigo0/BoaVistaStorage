package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.InsumoProduzido;
import com.ibdev.boavistastorage.entity.Produzido;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.repository.ProduzidoRepository;

import java.util.List;

public class ProduzidoService {
    private ProduzidoRepository produzidoRepository;

    public ProduzidoService(ProduzidoRepository produzidoRepository) {
        this.produzidoRepository = produzidoRepository;
    }

    public void salvarProduzido(String nome, String descricao, String tipo, double precoVenda) {
        if(nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do produzido não pode ser vazio.");
        }
        if(descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição do produzido não pode ser vazia.");
        }
        if(tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("Tipo do produzido não pode ser vazio.");
        }
        if(precoVenda < 0) {
            throw new IllegalArgumentException("Preço de venda não pode ser negativo.");
        }
        Produzido produzido = new Produzido(nome, descricao, tipo, precoVenda);
        produzidoRepository.create(produzido);
    }

    public Produzido buscarProduzidoPorId(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID do produzido inválido.");
        }
        Produzido produzido = produzidoRepository.findById(id);
        if(produzido == null) {
            throw new RuntimeException("Produzido não encontrado com o ID: " + id);
        }
        return produzido;
    }

    public Produzido buscarProduzidoPorNome(String nome) {
        if(nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do produzido não pode ser vazio.");
        }
        Produzido produzido = produzidoRepository.findByNome(nome);
        if(produzido == null) {
            throw new RuntimeException("Produzido não encontrado com o Nome: " + nome);
        }
        return produzido;
    }

    public Produzido buscarProduzidoPorTipo (String tipo) {
        if(tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("Tipo do produzido não pode ser vazio.");
        }
        Produzido produzido = produzidoRepository.findByTipo(tipo);
        if(produzido == null) {
            throw new RuntimeException("Produzido não encontrado com o Tipo: " + tipo);
        }
        return produzido;
    }

    public List<InsumoProduzido> buscarTodosProduzidos() {
        List<InsumoProduzido> insumosProduzidos = produzidoRepository.findAll();
        if(insumosProduzidos.isEmpty()) {
            throw new RuntimeException("Nenhum insumo produzido encontrado.");
        }
        return insumosProduzidos;
    }

    public Produzido buscarProduzidoPorPrecoCusto(double precoCusto) {
        if(precoCusto < 0) {
            throw new IllegalArgumentException("Preço de custo não pode ser negativo.");
        }
        Produzido produzido = produzidoRepository.findByPrecoCusto(precoCusto);
        if(produzido == null) {
            throw new RuntimeException("Produto não encontrado com o preço de custo: " + precoCusto);
        }
        return produzido;
    }

    public Produzido buscarProduzidoPorPrecoVenda(double precoVenda) {
        if(precoVenda < 0) {
            throw new IllegalArgumentException("Preço de venda não pode ser negativo.");
        }
        Produzido produzido = produzidoRepository.findByPrecoVenda(precoVenda);
        if(produzido == null) {
            throw new RuntimeException("Produzido não encontrado com o preço de venda: " + precoVenda);
        }
        return produzido;
    }

    public void atualizarProduzido(Long id, Produzido produzido) {
        if (produzido == null || id == null || id <= 0) {
            throw new IllegalArgumentException("Produzido inválido ou sem ID.");
        }
        try {
            produzidoRepository.update(id, produzido);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar Produzido: " + e.getMessage());
        }
    }

    public void listarProduzidos() {
        produzidoRepository.readAll();
    }

    public void deletarProduzido(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID do produzido inválido.");
        }
        produzidoRepository.delete(id);
    }
}
