package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Gerente;
import jakarta.persistence.EntityManager;

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

    public Gerente findByEmail(String endereco) {
        return em.createQuery("select g from Gerente g where g.endereco = :endereco", Gerente.class)
                .setParameter("endereco", endereco)
                .getSingleResult();
    }

    public Gerente findByCPF(String CPF) {
        return em.createQuery("select g from Gerente g where g.CPF = :cpf", Gerente.class)
                .setParameter("cpf", CPF)
                .getSingleResult();
    }

    public void update(Long idGerente, Gerente atendente) {
        try {
            em.getTransaction().begin();

            Gerente gerenteDB = em.find(Gerente.class, idGerente);

            if (gerenteDB != null) {
                gerenteDB.setNome(atendente.getNome());
                gerenteDB.setTelefone(atendente.getTelefone());
                gerenteDB.setCPF(atendente.getCPF());
                gerenteDB.setEndereco(atendente.getEndereco());
                gerenteDB.setLogin(atendente.getLogin());
                gerenteDB.setSenha(atendente.getSenha());
            } else {
                System.out.println("Gerente não encontrado!");
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
            } throw new RuntimeException("Erro ao deletar o Gerente: " + e.getMessage());
        }
    }
}
