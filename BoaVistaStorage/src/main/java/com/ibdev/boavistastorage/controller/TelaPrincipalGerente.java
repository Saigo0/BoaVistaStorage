package com.ibdev.boavistastorage.controller;

import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaPrincipalGerente implements Initializable {
    private EntityManager entityManager;

    @FXML
    private Button btnFinalizar;

    @FXML
    private GridPane gridProdutos;

    @FXML
    private ImageView iconConfig;

    @FXML
    private ImageView iconFinanceiro;

    @FXML
    private ImageView iconFinanceiro1;

    @FXML
    private ImageView iconFinanceiro11;

    @FXML
    private ImageView iconInicio;

    @FXML
    private ImageView iconPedidos;

    @FXML
    private ImageView iconSair;

    @FXML
    private Label labelTotal;

    @FXML
    private ImageView logoView;

    @FXML
    private VBox orderItemsBox;

    @FXML
    private VBox sidebar;

    @FXML
    private Label tituloPedido;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
