package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.repository.CardapioRepository;

public class CardapioService {
    private static CardapioRepository cardapioRepository;

    public CardapioService(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    public void createCardapio(Cardapio cardapio) {
        cardapioRepository.create(cardapio);
    }

    public Cardapio findCardapioById(Long id) {
        return cardapioRepository.findById(id);
    }

    public void updateCardapio(Cardapio cardapio) {
        cardapioRepository.update(cardapio);
    }

    public void readAllCardapios() {
        cardapioRepository.readAll();
    }

    public void deleteCardapio(Long id) {
        cardapioRepository.delete(id);
    }

    public void addProdutoAoCardapio(Long idCardapio, Produto produto) {
        Cardapio cardapio = cardapioRepository.findById(idCardapio);
        if (cardapio != null) {
            cardapio.addProdutosDisponiveis(produto);
            produto.setCardapio(cardapio);
            cardapioRepository.update(cardapio);
        } else {
            throw new RuntimeException("Cardápio não encontrado.");
        }
    }

    public void removeProdutoDoCardapio(Long idCardapio, Produto produto) {
        Cardapio cardapio = cardapioRepository.findById(idCardapio);
        if (cardapio != null) {
            cardapio.removeProdutosDisponiveis(produto);
            produto.setCardapio(null);
            cardapioRepository.update(cardapio);
        } else {
            throw new RuntimeException("Cardápio não encontrado.");
        }
    }
}
