package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Insumo;
import com.ibdev.boavistastorage.entity.StatusEstoque;
import jakarta.persistence.EntityManager;

import java.util.List;

public class InsumoRepository {
    private EntityManager em;
    
    public InsumoRepository(EntityManager em) {
        this.em = em;
    }

    public boolean create(Insumo insumo) {
        try {
            em.persist(insumo);
            return true;
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

    public List<Insumo> findAll() {
        return em.createQuery("select i from Insumo i", Insumo.class)
                .getResultList();
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

    public boolean update(Long idInsumo, Insumo insumo) {
        try {
            em.getTransaction().begin();

            Insumo insumoDB = em.find(Insumo.class, idInsumo);

            if (insumoDB != null) {
                insumoDB.setNome(insumo.getNome());
                insumoDB.setPrecoCusto(insumo.getPrecoCusto());
                insumoDB.setUnidadeDeMedida(insumo.getUnidadeDeMedida());
                insumoDB.setUnidadeDeMedida(insumo.getUnidadeDeMedida());
                insumoDB.setQuantidadeEstoque(insumo.getQuantidadeEstoque());
                insumoDB.setStatusEstoque(insumo.getStatusEstoque());
                insumoDB.setCardapio(insumo.getCardapio());
                insumoDB.setInsumosProduzidos(insumo.getInsumosProduzidos());
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
        return true;
    }

    public boolean delete(Long id) {
        try {
            em.getTransaction().begin();
            em.remove(em.find(Insumo.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            } throw new RuntimeException("Erro ao deletar o Insumo: " + e.getMessage());
        }
        return true;
    }
}
