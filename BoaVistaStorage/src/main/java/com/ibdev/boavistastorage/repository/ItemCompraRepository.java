package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Cliente;
import com.ibdev.boavistastorage.entity.Fornecimento;
import com.ibdev.boavistastorage.entity.ItemCompra;
import jakarta.persistence.EntityManager;

public class ItemCompraRepository {
    private EntityManager em;

    public ItemCompraRepository(EntityManager em) {
        this.em = em;
    }

    public boolean create(ItemCompra itemCompra) {
        try {
            em.getTransaction().begin();
            em.persist(itemCompra);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar itemCompra: " + e.getMessage());
        }
    }

    public boolean update(Long id, ItemCompra itemCompra) {
        try {
            em.getTransaction().begin();

            ItemCompra itemCompraDB = em.find(ItemCompra.class, id);

            if (itemCompraDB != null) {
                itemCompraDB.setFornecimento(itemCompra.getFornecimento());
                itemCompraDB.setCompra(itemCompra.getCompra());
                itemCompraDB.setQuantidade(itemCompra.getQuantidade());
                itemCompraDB.setPrecoCusto(itemCompra.getPrecoCusto());
            } else {
                System.out.println("Fornecimento não encontrado!");
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

    public boolean delete(Long idItemCompra) {
        try {
            em.getTransaction().begin();
            em.remove(em.find(Cliente.class, idItemCompra));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar itemCompra: " + e.getMessage());
        }
    }
}
