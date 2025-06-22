package com.ibdev.boavistastorage.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaCompraProduto {

    @FXML
    private TextField fornecedorField;

    @FXML
    private TextField produtoField;

    @FXML
    private TextField quantidadeField;

    @FXML
    private TableView<?> tabelaProdutos;

    @FXML
    private Label totalLabel;

    public void initialize(URL location, ResourceBundle resources) {

    }

}
