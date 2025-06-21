package com.ibdev.boavistastorage.main;

import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.repository.CardapioRepository;
import com.ibdev.boavistastorage.repository.VendavelRepository;
import com.ibdev.boavistastorage.service.CardapioService;
import com.ibdev.boavistastorage.service.VendavelService;
import javafx.application.Application;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class Main extends Application {
    private static EntityManager em;
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("jpa_basic");
        em = emf.createEntityManager();
        CardapioRepository cardapioRepository = new CardapioRepository(em);
        CardapioService cardapioService = new CardapioService(cardapioRepository);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneManager.init(primaryStage, em); // Passe o EntityManager aqui
        SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-login.fxml", "Login - Boa Vista Storage");
    }

    @Override
    public void stop() {
        System.out.println("Fechando a aplicação e os recursos do banco de dados...");
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        System.out.println("Recursos liberados. Aplicação encerrada.");
    }
}