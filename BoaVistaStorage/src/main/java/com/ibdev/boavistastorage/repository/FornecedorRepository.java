package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Atendente;
import com.ibdev.boavistastorage.entity.Fornecedor;
import jakarta.persistence.EntityManager;

public class FornecedorRepository {
    private EntityManager em;

    public FornecedorRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Fornecedor fornecedor) {
        try {
            em.getTransaction().begin();
            em.persist(fornecedor);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar fornecedor: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select f from Atendente f", Atendente.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Fornecedor findByCPF(String nome) {
        return em.createQuery("select f from Atendente f where f.nome = :nome", Fornecedor.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public Fornecedor findById(Long id) {
        return em.find(Fornecedor.class, id);
    }

    public Fornecedor findByEmail(String email) {
        return em.createQuery("select f from Fornecedor f where f.email = :email", Fornecedor.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Fornecedor findByCNPJ(String CNPJ) {
        return em.createQuery("select f from Fornecedor f where f.CNPJ = :cnpj", Fornecedor.class)
                .setParameter("cnpj", CNPJ)
                .getSingleResult();
    }


    public void update(Long idFornecedor, Fornecedor fornecedor) {
        try {
            em.getTransaction().begin();

            Fornecedor fornecedorDB = em.find(Fornecedor.class, idFornecedor);

            if (fornecedorDB != null) {
                fornecedorDB.setNome(fornecedor.getNome());
                fornecedorDB.setCNPJ(fornecedor.getCNPJ());
                fornecedorDB.setTelefone(fornecedor.getTelefone());
                fornecedorDB.setEmail(fornecedor.getEmail());
                fornecedorDB.setEndereco(fornecedor.getEndereco());
                fornecedorDB.setCidade(fornecedor.getCidade());
            } else {
                System.out.println("Fornecedor não encontrado!");
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
            em.remove(em.find(Fornecedor.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        } throw new RuntimeException("Erro ao deletar o fornecedor: " + e.getMessage());
        }
    }
}
