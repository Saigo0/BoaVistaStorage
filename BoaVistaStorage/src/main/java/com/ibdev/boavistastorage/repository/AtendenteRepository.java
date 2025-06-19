package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Atendente;
import com.ibdev.boavistastorage.entity.Atendente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class    AtendenteRepository {
    private EntityManager em;

    public AtendenteRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Atendente atendente) {
        try {
            em.getTransaction().begin();
            em.persist(atendente);
            em.getTransaction().commit();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw new RuntimeException("Atendente já cadastrado com os mesmos dados.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar atendente: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select a from Atendente a", Atendente.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Atendente findById(Long id) {
        return em.find(Atendente.class, id);
    }

    public Atendente findByName(String nome) {
        return em.createQuery("select a from Atendente a where a.nome = :nome", Atendente.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public void findByTelefone(String telefone) {
        em.createQuery("select a from Atendente a where a.telefone = :telefone", Atendente.class)
                .setParameter("telefone", telefone)
                .getResultList()
                .forEach(System.out::println);
    }


    public Atendente findByCPF(String CPF) {
        return em.createQuery("select a from Atendente a where a.CPF = :cpf", Atendente.class)
                .setParameter("cpf", CPF)
                .getSingleResult();
    }

    public void findByEndereco(String endereco) {
        em.createQuery("select a from Atendente a where a.endereco = :endereco", Atendente.class)
                .setParameter("endereco", endereco)
                .getResultList()
                .forEach(System.out::println);
    }

    public Atendente findByLogin(String login) {
        return em.createQuery("select a from Atendente a where a.login = :login", Atendente.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    public void update(Long idAtendente, Atendente atendente) {
        try {
            em.getTransaction().begin();

            Atendente atendenteDB = em.find(Atendente.class, idAtendente);

            if (atendenteDB != null) {
                atendenteDB.setNome(atendente.getNome());
                atendenteDB.setTelefone(atendente.getTelefone());
                atendenteDB.setCPF(atendente.getCPF());
                atendenteDB.setEndereco(atendente.getEndereco());
                atendenteDB.setLogin(atendente.getLogin());
                atendenteDB.setSenha(atendente.getSenha());
            } else {
                System.out.println("Atendente não encontrado!");
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
            em.remove(em.find(Atendente.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            } throw new RuntimeException("Erro ao deletar o Atendente: " + e.getMessage());
        }
    }
}
