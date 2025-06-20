package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Gerente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

public class GerenteRepository {
    private EntityManager em;

    public GerenteRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Gerente gerente) {
        try {
            em.getTransaction().begin();
            em.persist(gerente);
            em.getTransaction().commit();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw new RuntimeException("Fornecedor já cadastrado com os mesmos dados.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar gerente: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select g from Gerente g", Gerente.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Gerente findById(Long id) {
        return em.find(Gerente.class, id);
    }

    public Gerente findByName(String nome) {
        return em.createQuery("select g from Gerente g where g.nome = :nome", Gerente.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public Gerente findByTelefone(String telefone) {
        return em.createQuery("select g from Gerente g where g.telefone = :telefone", Gerente.class)
                .setParameter("telefone", telefone)
                .getSingleResult();
    }

    public Gerente findByCPF(String CPF) {
        return em.createQuery("select g from Gerente g where g.CPF = :cpf", Gerente.class)
                .setParameter("cpf", CPF)
                .getSingleResult();
    }

    public Gerente findByEndereco(String endereco) {
        return em.createQuery("select g from Gerente g where g.endereco = :endereco", Gerente.class)
                .setParameter("endereco", endereco)
                .getSingleResult();
    }

    public Gerente findByLogin(String login) {
        return em.createQuery("select g from Gerente g where g.login = :login", Gerente.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    public Gerente findBySenha(String senha) {
        return em.createQuery("select g from Gerente g where g.senha = :senha", Gerente.class)
                .setParameter("senha", senha)
                .getSingleResult();
    }

    public Gerente findByLoginAndSenha(String login, String senha) {
        try {
            return em.createQuery("select g from Gerente g where g.login = :login and g.senha = :senha", Gerente.class)
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof jakarta.persistence.NoResultException) {
                throw new RuntimeException("Login ou senha inválidos.");
            } else {
                throw new RuntimeException("Erro ao realizar a consulta por login e senha: " + ex.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar a consulta por login e senha: " + e.getMessage());
        }
    }

    public void update(Long idGerente, Gerente gerente) {
        try {
            em.getTransaction().begin();

            Gerente gerenteDB = em.find(Gerente.class, idGerente);

            if (gerenteDB != null) {
                gerenteDB.setNome(gerente.getNome());
                gerenteDB.setTelefone(gerente.getTelefone());
                gerenteDB.setCPF(gerente.getCPF());
                gerenteDB.setEndereco(gerente.getEndereco());
                gerenteDB.setLogin(gerente.getLogin());
                gerenteDB.setSenha(gerente.getSenha());
                em.merge(gerenteDB);
            } else {
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
            em.remove(em.find(Gerente.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao deletar o Gerente: " + e.getMessage());
        }
    }
}
