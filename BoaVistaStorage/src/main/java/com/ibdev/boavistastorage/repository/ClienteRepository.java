package com.ibdev.boavistastorage.repository;

import com.ibdev.boavistastorage.entity.Cliente;
import com.ibdev.boavistastorage.entity.Fornecedor;
import jakarta.persistence.EntityManager;

public class ClienteRepository {
    private EntityManager em;

    public ClienteRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public void readAll() {
        em.createQuery("select c from Cliente c", Cliente.class)
                .getResultList()
                .forEach(System.out::println);
    }

    public Cliente findByName(String nome) {
        return em.createQuery("select c from Cliente c where c.nome = :nome", Cliente.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public Cliente findById(Long id) {
        return em.find(Cliente.class, id);
    }

    public Cliente findByCpf(String cpf) {
        return em.createQuery("select c from Cliente c where c.CPF = :cpf", Cliente.class)
                .setParameter("cpf", cpf)
                .getSingleResult();
    }


    public void update(Long idCliente, Cliente cliente) {
        try {
            em.getTransaction().begin();

            Cliente clienteDB = em.find(Cliente.class, idCliente);

            if (clienteDB != null) {
                clienteDB.setNome(cliente.getNome());
                clienteDB.setCPF(cliente.getCPF());
                clienteDB.setTelefone(cliente.getTelefone());
                clienteDB.setEndereco(cliente.getEndereco());
            } else {
                System.out.println("Cliente não encontrado!");
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
            em.remove(em.find(Cliente.class, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            } throw new RuntimeException("Erro ao deletar o cliente: " + e.getMessage());
        }
    }
}
