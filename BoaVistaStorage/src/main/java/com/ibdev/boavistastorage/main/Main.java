package com.ibdev.boavistastorage.main;

import com.ibdev.boavistastorage.entity.Fornecedor;
import com.ibdev.boavistastorage.repository.FornecedorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_basic");
        EntityManager em = emf.createEntityManager();
        FornecedorRepository fornecedorRepository = new FornecedorRepository(em);


        Fornecedor fornecedor = fornecedorRepository.findById(1L);

        fornecedor.setNome("Dagoberto");

        fornecedorRepository.update(1L, fornecedor);
        fornecedorRepository.readAll();

//        Fornecedor fornecedor2 = new Fornecedor();
//        fornecedorRepository.create(fornecedor2);

    }
}
