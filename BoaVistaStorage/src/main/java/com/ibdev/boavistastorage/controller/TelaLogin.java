package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.main.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaLogin implements Initializable {
    public TelaLogin() {
    }

    @FXML
    private Button btnEntrar;

    @FXML
    private TextField campoSenha;

    @FXML
    private TextField campoUser;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEntrar.setOnAction(event -> {
        System.out.println("Sucesosooo");
            String usuario = campoUser.getText();
            String senha = campoSenha.getText();



                Stage stage = (Stage) btnEntrar.getScene().getWindow();
                SceneManager.mudarCena(stage, "/com/ibdev/view/tela-principal-atendente.fxml", "Tela Principal Atendente");

        });
    }
}

