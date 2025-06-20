package com.ibdev.boavistastorage.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public static void mudarCena(Stage stage, String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao carregar a cena: " + fxmlPath, ex);
        }
    }
}
