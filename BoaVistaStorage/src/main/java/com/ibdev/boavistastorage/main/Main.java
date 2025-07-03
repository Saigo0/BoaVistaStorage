package com.ibdev.boavistastorage.main;

import com.ibdev.boavistastorage.entity.Gerente;
import com.ibdev.boavistastorage.entity.Insumo;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.main.SceneManager;
import com.ibdev.boavistastorage.repository.GerenteRepository;
import com.ibdev.boavistastorage.repository.InsumoRepository;
import com.ibdev.boavistastorage.repository.VendavelRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main extends Application {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("jpa_basic");
            em = emf.createEntityManager();
            System.out.println("EntityManager e EntityManagerFactory inicializados com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar EntityManagerFactory ou EntityManager: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        launch(args);


    }

    @Override
    public void start(Stage primaryStage) {
        SceneManager.init(primaryStage, em);
        System.out.println("SceneManager inicializado.");
        SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-login.fxml", "Tela Login - Boa Vista Storage");
    }

    @Override
    public void stop() {
        System.out.println("Fechando a aplicação e os recursos do banco de dados...");
        if (em != null && em.isOpen()) {
            em.close();
            System.out.println("EntityManager fechado.");
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory fechado.");
        }
        System.out.println("Recursos liberados. Aplicação encerrada.");
    }


}