package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.main.SceneManager;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaPrincipalAtendente implements Initializable {
    private EntityManager entityManager;

    public TelaPrincipalAtendente() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Iniciando TelaPrincipalAtendente");

        Platform.runLater(() -> {
            configurarEventos();
        });
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @FXML
    private HBox btnCardapio;

    @FXML
    private ImageView iconConfig;

    @FXML
    private ImageView iconSair;

    @FXML
    private ImageView logoView;

    @FXML
    private VBox sidebar;

    private void configurarEventos() {
        btnCardapio.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnCardapio.getScene().getWindow();
            SceneManager.mudarCena(stage, "/com/ibdev/view/tela-cardapio.fxml", "Tela Cardápio");
        });
    }
}
