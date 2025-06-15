package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Pedido;
import com.ibdev.boavistastorage.entity.Venda;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;

public class PedidoRepository {
    private EntityManager em;

    public PedidoRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Pedido pedido) {
        try {
            em.getTransaction().begin();
            em.persist(pedido);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar pedido: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select p from Pedido p", Pedido.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Pedido findById(Long id) {
        return em.find(Pedido.class, id);
    }

    public Pedido findByDataPedido(LocalDateTime dataPedido) {
        return em.createQuery("select p from Pedido p where p.dataPedido = :dataPedido", Pedido.class)
                .setParameter("dataPedido", dataPedido)
                .getSingleResult();
    }


    public void update(Long idPedido, Pedido pedido) {
        try {
            em.getTransaction().begin();

            Pedido pedidoDB = em.find(Pedido.class, idPedido);

            if (pedidoDB != null) {
                pedidoDB.setDataPedido(pedido.getDataPedido());
                pedidoDB.setVenda(pedido.getVenda());
            } else {
                System.out.println("Pedido não encontrada!");
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
            em.remove(em.find(Pedido.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            } throw new RuntimeException("Erro ao deletar o pedido: " + e.getMessage());
        }
    }
}
