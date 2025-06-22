package com.ibdev.boavistastorage.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaCRUDInsumo implements Initializable {


    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnCriar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TableColumn<?, ?> colAcoes;

    @FXML
    private TableColumn<?, ?> colFornecedor;

    @FXML
    private TableColumn<?, ?> colNome;

    @FXML
    private TableColumn<?, ?> colPreco;

    @FXML
    private TableColumn<?, ?> colUnidade;

    @FXML
    private TableView<?> tabelaInsumos;

    @FXML
    private TextField txtFornecedor;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtUnidade;

    public void initialize(URL location, ResourceBundle resources) {

    }


}
