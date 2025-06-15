package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Produzido;
import com.ibdev.boavistastorage.entity.Venda;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;

public class ProduzidoRepository {
    private EntityManager em;

    public ProduzidoRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Produzido produzido) {
        try {
            em.getTransaction().begin();
            em.persist(produzido);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar produzido: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select p from Produzido p", Produzido.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Produzido findByNome(String nome) {
        return em.createQuery("select p from Produzido p where p.nome = :nome", Produzido.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }



    public void update(Long idProduto, Produzido produzido) {
        try {
            em.getTransaction().begin();

            Produzido produzidoDB = em.find(Produzido.class, idProduto);

            if (produzidoDB != null) {
                produzidoDB.setDescricao(produzido.getDescricao());
                produzidoDB.setTipo(produzido.getTipo());
                produzidoDB.setPrecoVenda(produzido.getPrecoVenda());
                produzidoDB.setNome(produzido.getNome());
            } else {
                System.out.println("Produto produzido não encontrado!");
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
            em.remove(em.find(Produzido.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            } throw new RuntimeException("Erro ao deletar o produto produzido: " + e.getMessage());
        }
    }
}
