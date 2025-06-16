package com.ibdev.boavistastorage.main;

import com.ibdev.boavistastorage.entity.*;
import com.ibdev.boavistastorage.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_basic");
        EntityManager em = emf.createEntityManager();

        // Falta testar de gerente pra baixo
        // Relembrar que temos que testar a compra quando já estiver tudo implementado
    }
}
