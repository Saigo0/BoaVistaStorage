package com.ibdev.boavistastorage.main;

import com.ibdev.boavistastorage.controller.TelaLogin;
import com.ibdev.boavistastorage.entity.Atendente;
import com.ibdev.boavistastorage.entity.Gerente;
import com.ibdev.boavistastorage.repository.AtendenteRepository;
import com.ibdev.boavistastorage.repository.GerenteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_basic");
        EntityManager em = emf.createEntityManager();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_basic");
        EntityManager em = emf.createEntityManager();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ibdev/view/tela-login.fxml"));
        Parent root = loader.load();
        TelaLogin controller = loader.getController();
        controller.setEntityManager(em);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Login - Boa Vista Storage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
