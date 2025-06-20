package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Cliente;
import com.ibdev.boavistastorage.entity.Fornecimento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class FornecimentoRepository {
    private EntityManager em;

    public FornecimentoRepository(EntityManager em) {
        this.em = em;
    }

    public boolean create(Fornecimento fornecimento) {
        try {
            em.getTransaction().begin();
            em.persist(fornecimento);
            em.getTransaction().commit();

        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw new RuntimeException("Fornecimento já cadastrado com os mesmos dados.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar fornecimento: " + e.getMessage());
        }
        return true;
    }

    public boolean update(Long idFornecimento, Fornecimento fornecimento) {
        try {
            em.getTransaction().begin();

            Fornecimento fornecimentoDB = em.find(Fornecimento.class, idFornecimento);

            if (fornecimentoDB != null) {
                fornecimentoDB.setFornecedor(fornecimento.getFornecedor());
                fornecimentoDB.setInsumo(fornecimento.getInsumo());
                fornecimentoDB.setVendavel(fornecimento.getVendavel());
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

    public boolean delete(Long id) {
        try {
            em.getTransaction().begin();
            em.remove(em.find(Cliente.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar o fornecimento: " + e.getMessage());
        }
        return true;
    }
}
