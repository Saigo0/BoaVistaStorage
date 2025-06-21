package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.main.SceneManager;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaCardapio implements Initializable {
    private EntityManager entityManager;

    @FXML
    private HBox btnCardapio;

    @FXML
    private HBox btnCliente;

    @FXML
    private HBox btnInicio;

    @FXML
    private HBox btnLogout;

    @FXML
    private HBox btnPedidos;

    @FXML
    private HBox btnVenda;

    @FXML
    private GridPane gridCardapio;

    @FXML
    private Label insumos;

    @FXML
    private Label itemNome;

    @FXML
    private ImageView logoView;

    @FXML
    private Label precoVenda;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando TelaCardapio com EntityManager: " + entityManager);

        Platform.runLater(() -> {
            configurarEventos();
        });
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void configurarEventos() {
        btnInicio.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnInicio.getScene().getWindow();
            SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-principal-atendente.fxml", "Boa Vista Storage - Tela Principal");
        });

        btnLogout.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-login.fxml", "Login - Boa Vista Storage");
        });
    }
}
