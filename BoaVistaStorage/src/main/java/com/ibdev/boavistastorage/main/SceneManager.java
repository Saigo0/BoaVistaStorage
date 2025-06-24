package com.ibdev.boavistastorage.main;

import com.ibdev.boavistastorage.controller.*; // Certifique-se de que TelaPedidoAtendente está neste pacote ou importe-o
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;

import java.io.IOException;

public class SceneManager {

    private static Stage primaryStage;
    private static EntityManager entityManager;

    public static void init(Stage stage, EntityManager em) {
        if (primaryStage != null) {
            throw new IllegalStateException("SceneManager já foi inicializado.");
        }
        primaryStage = stage;
        entityManager = em; // Armazena o EntityManager aqui
    }

    public static void mudarCena(String fxmlPath, String titulo, EntityManager em) {
        if (primaryStage == null) {
            throw new IllegalStateException("SceneManager não foi inicializado. Chame init() primeiro.");
        }
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof TelaLogin) {
                ((TelaLogin) controller).setEntityManager(em);
            } else if (controller instanceof TelaCardapio) {
                ((TelaCardapio) controller).setEntityManager(em);
            }
            else if (controller instanceof TelaEstoqueGerente) {
                ((TelaEstoqueGerente) controller).setEntityManager(em);
                System.out.println("EntityManager setado na TelaEstoqueGerente (via SceneManager.mudarCena).");
            }
            // ADICIONE AQUI PARA TelaPedidoAtendente
            else if (controller instanceof TelaPedidoAtendente) {
                ((TelaPedidoAtendente) controller).setEntityManager(em);
                System.out.println("EntityManager setado na TelaPedidoAtendente (via SceneManager.mudarCena).");
            }
            // ... (outros controladores existentes) ...


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

    public static void mudarCenaMaximizada(String fxmlPath, String titulo, EntityManager em) {
        if (primaryStage == null) {
            throw new IllegalStateException("SceneManager não foi inicializado. Chame init() primeiro.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof TelaLogin) {
                ((TelaLogin) controller).setEntityManager(em);
            } else if (controller instanceof TelaCardapio) {
                ((TelaCardapio) controller).setEntityManager(em);
            }
            else if (controller instanceof TelaEstoqueGerente) {
                ((TelaEstoqueGerente) controller).setEntityManager(em);
                System.out.println("EntityManager setado na TelaEstoqueGerente (via SceneManager.mudarCenaMaximizada).");
            }
            // ADICIONE AQUI PARA TelaPedidoAtendente
            else if (controller instanceof TelaPedidoAtendente) {
                ((TelaPedidoAtendente) controller).setEntityManager(em);
                System.out.println("EntityManager setado na TelaPedidoAtendente (via SceneManager.mudarCenaMaximizada).");
            }
            else if(controller instanceof TelaCRUDVendaveis){
                ((TelaCRUDVendaveis) controller).setEntityManager(em);
            }
            // ... (outros controladores existentes) ...

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());

            primaryStage.setTitle(titulo);
            primaryStage.setScene(scene);
            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setMaximized(true);
            primaryStage.show();

        } catch (IOException ex) {
            System.err.println("Erro ao carregar a cena: " + fxmlPath);
            throw new RuntimeException("Falha ao carregar FXML: " + fxmlPath, ex);
        }
    }

    public static void mudarCena(String fxmlPath, String titulo) {
        // Esta sobrecarga usa o entityManager estático que foi setado no init()
        mudarCena(fxmlPath, titulo, entityManager);
    }

    public static void mudarCenaMaximizada(String fxmlPath, String titulo) {
        // Esta sobrecarga usa o entityManager estático que foi setado no init()
        mudarCenaMaximizada(fxmlPath, titulo, entityManager);
    }
}