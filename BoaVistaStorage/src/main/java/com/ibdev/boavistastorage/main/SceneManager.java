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
    private static jakarta.persistence.EntityManager entityManager;

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

            // Injetar o EntityManager no controlador, se necessário
            Object controller = loader.getController();
            if (controller instanceof com.ibdev.boavistastorage.controller.TelaLogin) {
                ((com.ibdev.boavistastorage.controller.TelaLogin) controller).setEntityManager(entityManager);
            }
            // Adicione outros controladores aqui, se necessário

            Scene scene = new Scene(root);

            primaryStage.setTitle(titulo);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException ex) {
            System.err.println("Erro ao carregar a cena: " + fxmlPath);
            throw new RuntimeException("Falha ao carregar FXML: " + fxmlPath, ex);
        }
    }

    public static void mudarCenaMaximizada(String fxmlPath, String titulo) {
        if (primaryStage == null || entityManager == null) {
            throw new IllegalStateException("SceneManager não foi inicializado. Chame init() primeiro.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Injetar o EntityManager no controlador, se necessário
            Object controller = loader.getController();
            if (controller instanceof com.ibdev.boavistastorage.controller.TelaLogin) {
                ((com.ibdev.boavistastorage.controller.TelaLogin) controller).setEntityManager(entityManager);
            }
            // Adicione outros controladores aqui, se necessário

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());

            primaryStage.setTitle(titulo);
            primaryStage.setScene(scene);
            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setMaximized(true); // aqui sim, com efeito real
            primaryStage.show();

        } catch (IOException ex) {
            System.err.println("Erro ao carregar a cena: " + fxmlPath);
            throw new RuntimeException("Falha ao carregar FXML: " + fxmlPath, ex);
        }
    }
}
