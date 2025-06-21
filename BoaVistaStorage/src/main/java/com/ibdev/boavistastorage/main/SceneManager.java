package com.ibdev.boavistastorage.main;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage primaryStage;
    // Adicione uma referência estática ao EntityManager
    private static jakarta.persistence.EntityManager entityManager; // Adicione esta linha

    public static void init(Stage stage, jakarta.persistence.EntityManager em) {
        if (primaryStage != null) {
            throw new IllegalStateException("SceneManager já foi inicializado.");
        }
        primaryStage = stage;
        entityManager = em;
    }

    public static void mudarCena(String fxmlPath, String titulo) {
        if (primaryStage == null || entityManager == null) {
            throw new IllegalStateException("SceneManager não foi inicializado. Chame init() primeiro.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof com.ibdev.boavistastorage.controller.TelaLogin) {
                ((com.ibdev.boavistastorage.controller.TelaLogin) controller).setEntityManager(entityManager);
            }
            // adicionar mais 'if instanceof' para outros controladores que precisem do EntityManager

            Scene scene = new Scene(root);

            primaryStage.setTitle(titulo);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException ex) {
            System.err.println("Erro ao carregar a cena: " + fxmlPath);
            throw new RuntimeException("Falha ao carregar FXML: " + fxmlPath, ex);
        }
    }

    public static void mudarCenaMaximizada(String fxmlPath, String titulo) {
        mudarCena(fxmlPath, titulo);
        primaryStage.setMaximized(true);
    }
}