package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.repository.VendavelRepository;

import java.util.List;

public class VendavelService {
    private VendavelRepository vendavelRepository;

    public VendavelService(VendavelRepository vendavelRepository) {
        this.vendavelRepository = vendavelRepository;
    }

    public boolean salvarVendavel(String nome, double precoCusto, double precoVenda, double quantidadeEstoque) {
        if(nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        if(precoCusto < 0) {
            throw new IllegalArgumentException("Preço de custo não pode ser negativo.");
        }
        if(precoVenda < 0) {
            throw new IllegalArgumentException("Preço de venda não pode ser negativo");
        }
        if(quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
        }
        Vendavel vendavel = new Vendavel(nome, precoCusto, precoVenda, quantidadeEstoque);
        return vendavelRepository.create(vendavel);
    }

    public Vendavel buscarVendavelPorId(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }
        Vendavel vendavel = vendavelRepository.findById(id);
        if(vendavel == null) {
            throw new RuntimeException("Produto não encontrado com o ID: " + id);
        }
        return vendavel;
    }

    public List<Vendavel> buscarTodosVendaveis() {
        List<Vendavel> vendaveis = vendavelRepository.findAll();
        if(vendaveis.isEmpty()) {
            throw new RuntimeException("Nenhum produto encontrado.");
        }
        return vendaveis;
    }

    public Vendavel buscarVendavelPorNome(String nome) {
        if(nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        Vendavel vendavel = vendavelRepository.findByNome(nome);
        if(vendavel == null) {
            throw new RuntimeException("Produto não encontrado com o nome: " + nome);
        }
        return vendavel;
    }

    public Vendavel buscarVendavelPorPrecoCusto(double precoCusto) {
        if(precoCusto < 0) {
            throw new IllegalArgumentException("Preço de custo não pode ser negativo.");
        }
        Vendavel vendavel = vendavelRepository.findByPrecoCusto(precoCusto);
        if(vendavel == null) {
            throw new RuntimeException("Produto não encontrado com o preço de custo: " + precoCusto);
        }
        return vendavel;
    }

    public Vendavel buscarVendavelPorPrecoVenda(double precoVenda) {
        if(precoVenda < 0) {
            throw new IllegalArgumentException("Preço de venda não pode ser negativo.");
        }
        Vendavel vendavel = vendavelRepository.findByPrecoVenda(precoVenda);
        if(vendavel == null) {
            throw new RuntimeException("Produto não encontrado com o preço de venda: " + precoVenda);
        }
        return vendavel;
    }

    public Vendavel buscarVendavelPorQuantidadeEstoque(double quantidadeEstoque) {
        if(quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
        }
        Vendavel vendavel = vendavelRepository.findByQuantidadeEstoque(quantidadeEstoque);
        if(vendavel == null) {
            throw new RuntimeException("Produto não encontrado com a quantidade em estoque: " + quantidadeEstoque);
        }
        return vendavel;
    }

    public boolean atualizarVendavel(Long idVendavel, Vendavel vendavel) {
        if(idVendavel == null || idVendavel <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }
        if(vendavel == null) {
            throw new IllegalArgumentException("Objeto Vendavel não pode ser nulo.");
        }
        return vendavelRepository.update(idVendavel, vendavel);
    }

    public void listarVendaveis() {
        vendavelRepository.readAll();
    }

    public void deletarVendavel(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }
        vendavelRepository.delete(id);
    }
}
