package com.ibdev.boavistastorage.controller;


import com.ibdev.boavistastorage.main.SceneManager;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaPrincipalAtendente implements Initializable {
    private EntityManager entityManager;

    @FXML
    private TableColumn<?, ?> VendaC1liente;

    @FXML
    private TableColumn<?, ?> VendaCliente;

    @FXML
    private HBox btnCardapio;

    @FXML
    private HBox btnCliente;

    @FXML
    private HBox btnInicio;

    @FXML
    private HBox btnLogout;

    @FXML
    private Button btnNovaVenda;

    @FXML
    private Button btnNovoCliente;

    @FXML
    private Button btnNovoPedido;

    @FXML
    private HBox btnPedidos;

    @FXML
    private HBox btnVenda;

    @FXML
    private TableColumn<?, ?> data;

    @FXML
    private TableColumn<?, ?> idPedido;

    @FXML
    private TableColumn<?, ?> idVenda;

    @FXML
    private ImageView logoView;

    @FXML
    private TableView<?> tabelaPedidos;

    @FXML
    private TableView<?> tabelaVendas;

    @FXML
    private TableColumn<?, ?> vendaData;

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


    private void configurarEventos() {
        btnCardapio.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnCardapio.getScene().getWindow();
            SceneManager.mudarCena("/com/ibdev/view/tela-cardapio.fxml", "Tela Cardápio");
        });

        btnLogout.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            SceneManager.mudarCena("/com/ibdev/view/tela-login.fxml", "Tela Login");
        });
    }
}
