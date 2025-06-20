package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Atendente;
import com.ibdev.boavistastorage.entity.Fornecedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

import java.sql.SQLException;
import java.util.List;

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
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw new RuntimeException("Fornecedor já cadastrado com os mesmos dados.");
            }
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar fornecedor: " + ex.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select f from Fornecedor f", Fornecedor.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Fornecedor findById(Long id) {
        return em.find(Fornecedor.class, id);
    }

    public Fornecedor findByNome(String nome) {
        return em.createQuery("select f from Fornecedor f where f.nome = :nome", Fornecedor.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public Fornecedor findByCNPJ(String CNPJ) {
        return em.createQuery("select f from Fornecedor f where f.CNPJ = :cnpj", Fornecedor.class)
                .setParameter("cnpj", CNPJ)
                .getSingleResult();
    }

    public Fornecedor findByTelefone(String telefone) {
        return em.createQuery("select f from Fornecedor f where f.telefone = :telefone", Fornecedor.class)
                .setParameter("telefone", telefone)
                .getSingleResult();
    }

    public Fornecedor findByEmail(String email) {
        return em.createQuery("select f from Fornecedor f where f.email = :email", Fornecedor.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Fornecedor findByEndereco(String endereco) {
        return em.createQuery("select f from Fornecedor f where f.endereco = :endereco", Fornecedor.class)
                .setParameter("endereco", endereco)
                .getSingleResult();
    }

    public List<Fornecedor> findByCidade(String cidade) {
        return em.createQuery("select f from Fornecedor f where f.cidade = :cidade", Fornecedor.class)
                .setParameter("cidade", cidade)
                .getResultList();
    }

    public boolean update(Long idFornecedor, Fornecedor fornecedor) {
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
        return true;
    }

    public boolean delete(Long id) {
        try {
            em.getTransaction().begin();
            em.remove(em.find(Fornecedor.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar o fornecedor: " + e.getMessage());
        }
        return true;
    }
}
