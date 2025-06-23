package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.InsumoProduzido;
import com.ibdev.boavistastorage.entity.Produzido;
import com.ibdev.boavistastorage.entity.Venda;
import com.ibdev.boavistastorage.entity.Vendavel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.time.LocalDateTime;
import java.util.List;

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

    public Produzido findById(Long id) {
        Produzido produzido = em.find(Produzido.class, id);
        if (produzido == null) {
            throw new RuntimeException("Produzido não encontrado com o ID: " + id);
        }
        return produzido;
    }

    public Produzido findByTipo(String tipo) {
        return em.createQuery("select p from Produzido p where p.tipo = :tipo", Produzido.class)
                .setParameter("tipo", tipo)
                .getSingleResult();
    }

    public Produzido findByPrecoCusto(double precoCusto) {
        return em.createQuery("select p from Produzido p where p.precoCusto = :precoCusto", Produzido.class)
                .setParameter("precoCusto", precoCusto)
                .getSingleResult();
    }

    public Produzido findByPrecoVenda(double precoVenda) {
        return em.createQuery("select p from Produzido p where p.precoVenda = :precoVenda", Produzido.class)
                .setParameter("precoVenda", precoVenda)
                .getSingleResult();
    }

    public List<InsumoProduzido> findAll() {
        try {
            return em.createQuery("select p from InsumoProduzido p", InsumoProduzido.class)
                    .getResultList();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao buscar todos os produzidos: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todos os produzidos: " + e.getMessage());
        }
    }

    public void update(Long idProduto, Produzido produzido) {
        try {
            em.getTransaction().begin();

            Produzido produzidoDB = em.find(Produzido.class, idProduto);

            if (produzidoDB != null) {
                produzidoDB.setItemPedido(produzido.getItemPedido());
                produzidoDB.setPrecoCusto(produzido.getPrecoCusto());
                produzidoDB.setDescricao(produzido.getDescricao());
                produzidoDB.setTipo(produzido.getTipo());
                produzidoDB.setPrecoVenda(produzido.getPrecoVenda());
                produzidoDB.setNome(produzido.getNome());
                produzidoDB.setCardapio(produzido.getCardapio());
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
