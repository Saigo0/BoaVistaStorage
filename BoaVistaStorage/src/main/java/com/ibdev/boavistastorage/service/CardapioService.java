package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.repository.CardapioRepository;

public class CardapioService {
    private static CardapioRepository cardapioRepository;

    public CardapioService(CardapioService cardapioService) {
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
}
