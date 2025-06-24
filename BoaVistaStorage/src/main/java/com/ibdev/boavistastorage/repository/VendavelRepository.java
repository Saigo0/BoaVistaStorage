package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Fornecedor;
import com.ibdev.boavistastorage.entity.Venda;
import com.ibdev.boavistastorage.entity.Vendavel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class VendavelRepository {
    private EntityManager em;

    public VendavelRepository(EntityManager em) {
        this.em = em;
    }

    public boolean create(Vendavel vendavel) {
        try {
            em.getTransaction().begin();
            em.persist(vendavel);
            em.getTransaction().commit();
            return true;
        } catch (PersistenceException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Vendavel já cadastrado com os mesmos dados.");
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar vendavel: " + ex.getMessage());
        }
    }


    public void readAll() {
        em.createQuery("select v from Vendavel v", Vendavel.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Vendavel findById(Long id) {
        return em.find(Vendavel.class, id);
    }

    public List<Vendavel> findAll() {
        try {
            return em.createQuery("select v from Vendavel v", Vendavel.class)
                    .getResultList();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao buscar todos os vendaveis: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os vendaveis: " + e.getMessage());
        }
    }

    public Vendavel findByNome(String nome) {
        return em.createQuery("select v from Vendavel v where v.nome = :nome", Vendavel.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public Vendavel findByPrecoCusto(double precoCusto) {
        return em.createQuery("select v from Vendavel v where v.precoCusto = :precoCusto", Vendavel.class)
                .setParameter("precoCusto", precoCusto)
                .getSingleResult();
    }

    public Vendavel findByQuantidadeEstoque(double quantEstoque) {
        return em.createQuery("select v from Vendavel v where v.quantEstoque = :quantEstoque", Vendavel.class)
                .setParameter("quantEstoque", quantEstoque)
                .getSingleResult();
    }

    public Vendavel findByPrecoVenda(double precoVenda) {
        return em.createQuery("select v from Vendavel v where v.precoVenda = :precoVenda", Vendavel.class)
                .setParameter("precoVenda", precoVenda)
                .getSingleResult();
    }

    public boolean update(Long idVendavel, Vendavel vendavel) {
        try {
            em.getTransaction().begin();

            Vendavel vendavelDB = em.find(Vendavel.class, idVendavel);

            if (vendavelDB != null) {
                vendavelDB.setNome(vendavel.getNome());
                vendavelDB.setPrecoCusto(vendavel.getPrecoCusto());
                vendavelDB.setCardapio(vendavel.getCardapio());
                vendavelDB.setItemPedido(vendavel.getItemPedido());
                vendavelDB.setQuantEstoque(vendavel.getQuantEstoque());
                vendavelDB.setPrecoVenda(vendavel.getPrecoVenda());
                vendavelDB.setStatusEstoque(vendavel.getStatusEstoque());
            } else {
                System.out.println("Vendavel não encontrado!");
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

    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            em.remove(em.find(Vendavel.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar o vendavel: " + e.getMessage());
        }
    }
}
