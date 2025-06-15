package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Fornecedor;
import com.ibdev.boavistastorage.entity.Venda;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;

public class VendaRepository {
    private EntityManager em;

    public VendaRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Venda venda) {
        try {
            em.getTransaction().begin();
            em.persist(venda);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar venda: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select v from Venda v", Venda.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Venda findById(Long id) {
        return em.find(Venda.class, id);
    }

    public Venda findByDataVenda(LocalDateTime dataVenda) {
        return em.createQuery("select v from Venda v where v.dataVenda = :dataVenda", Venda.class)
                .setParameter("dataVenda", dataVenda)
                .getSingleResult();
    }


    public void update(Long idVenda, Venda venda) {
        try {
            em.getTransaction().begin();

            Venda vendaDB = em.find(Venda.class, idVenda);

            if (vendaDB != null) {
                vendaDB.setPedido(venda.getPedido());
                vendaDB.setDataVenda(venda.getDataVenda());
                vendaDB.setCliente(venda.getCliente());
                vendaDB.setValorTotal(venda.getValorTotal());
                vendaDB.setCliente(venda.getCliente());
                vendaDB.setFinanceiro(venda.getFinanceiro());
            } else {
                System.out.println("Venda não encontrada!");
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
            em.remove(em.find(Venda.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            } throw new RuntimeException("Erro ao deletar a venda: " + e.getMessage());
        }
    }
}
