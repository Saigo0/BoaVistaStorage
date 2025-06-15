package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Insumo;
import com.ibdev.boavistastorage.entity.StatusEstoque;
import jakarta.persistence.EntityManager;

public class InsumoRepository {
    private EntityManager em;
    
    public InsumoRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Insumo insumo) {
        try {
            em.getTransaction().begin();
            em.persist(insumo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar Insumo: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select i from Insumo i", Insumo.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Insumo findById(Long id) {
        return em.find(Insumo.class, id);
    }

    public Insumo findByName(String nome) {
        return em.createQuery("select i from Insumo i where i.nome = :nome", Insumo.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public Insumo findByPrecoCusto(double precoCusto) {
        return em.createQuery("select i from Insumo i where i.precoCusto = :precoCusto", Insumo.class)
                .setParameter("precoCusto", precoCusto)
                .getSingleResult();
    }

    public Insumo findByQuantidadeEstoque(double quantidadeEstoque) {
        return em.createQuery("select i from Insumo i where i.quantidadeEstoque = :quantidadeEstoque", Insumo.class)
                .setParameter("quantidadeEstoque", quantidadeEstoque)
                .getSingleResult();
    }

    public Insumo findByStatusEstoque(StatusEstoque statusEstoque) {
        return em.createQuery("select i from Insumo i where i.statusEstoque = :statusEstoque", Insumo.class)
                .setParameter("statusEstoque", statusEstoque)
                .getSingleResult();
    }

    public void update(Long idInsumo, Insumo insumo) {
        try {
            em.getTransaction().begin();

            Insumo insumoDB = em.find(Insumo.class, idInsumo);

            if (insumoDB != null) {
                insumoDB.setNome(insumoDB.getNome());
                insumoDB.setPrecoCusto(insumoDB.getPrecoCusto());
                insumoDB.setUnidadeDeMedida(insumoDB.getUnidadeDeMedida());
                insumoDB.setUnidadeDeMedida(insumoDB.getUnidadeDeMedida());
                insumoDB.setQuantidadeEstoque(insumoDB.getQuantidadeEstoque());
                insumoDB.setStatusEstoque(insumoDB.getStatusEstoque());
            } else {
                System.out.println("Insumo não encontrado!");
                throw new RuntimeException("Erro ao realizar a consulta por ID.");
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao realizar a consulta por ID." + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            em.remove(em.find(Insumo.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            } throw new RuntimeException("Erro ao deletar o Insumo: " + e.getMessage());
        }
    }
}
