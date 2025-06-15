package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.entity.Fornecedor;
import jakarta.persistence.EntityManager;

public class CardapioRepository {
    private EntityManager em;

    public CardapioRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Cardapio cardapio) {
        try {
            em.getTransaction().begin();
            em.persist(cardapio);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar cardapio: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select c from Cardapio c", Cardapio.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Cardapio findById(Long id) {
        return em.find(Cardapio.class, id);
    }

    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            em.remove(em.find(Cardapio.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            } throw new RuntimeException("Erro ao deletar o cardapio: " + e.getMessage());
        }
    }
}
