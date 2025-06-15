package com.ibdev.boavistastorage.main;

import com.ibdev.boavistastorage.entity.*;
import com.ibdev.boavistastorage.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_basic");
        EntityManager em = emf.createEntityManager();

        CardapioRepository cardapioRepository = new CardapioRepository(em);
//        Cardapio cardapio = new Cardapio();

        ProduzidoRepository produzidoRepository = new ProduzidoRepository(em);
        Produzido produzido = new Produzido("Pizza de Calabresa", "Com trigo", "Comida", 50.0);


        InsumoRepository insumoRepository = new InsumoRepository(em);
        Insumo insumo = new Insumo("Queijo", 5.0, "Kg", 10);

//        produzidoRepository.create(produzido);
//        insumoRepository.create(insumo);

//        cardapioRepository.create(cardapio);

        Cardapio cardapio = cardapioRepository.findById(1L);

        Insumo insumo1 = insumoRepository.findById(3L);
        insumo1.setCardapio(cardapio);
        insumoRepository.update(3L, insumo1);

        Produzido produzido1 = produzidoRepository.findById(1L);
        produzido1.setCardapio(cardapio);
        produzidoRepository.update(1L, produzido1);

//        cardapio.addProdutosDisponiveis(insumo1);
//        cardapio.addProdutosDisponiveis(produzido1);

//        for(Produto produto : cardapio.getProdutosDisponiveis()) {
//            cardapio.removeProdutosDisponiveis(produto);
//        }
        System.out.println("Antes");
        cardapioRepository.readAll();

        Cardapio cardapio1 = cardapioRepository.findById(1L);
        cardapio1.addProdutosDisponiveis(produzido);
        cardapioRepository.update(cardapio1);

//        cardapioRepository.update(cardapio);
        System.out.println("Depois");
        cardapioRepository.readAll();

    }
}
