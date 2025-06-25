package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Produto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProdutoRepository {
    private EntityManager entityManager;

    public ProdutoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Produto> findAll() {
        try {
            return entityManager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao buscar todos os produtos: " + e.getMessage());
        }
    }

    public Produto findById(Long id) {
        return entityManager.find(Produto.class, id);
    }

    public List<Produto> findAvailableForMenu() {
        return findAll();
    }
}
