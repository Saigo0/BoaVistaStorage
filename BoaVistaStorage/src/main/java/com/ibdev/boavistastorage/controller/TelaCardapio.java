package com.ibdev.boavistastorage.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaCardapio implements Initializable {
    public TelaCardapio() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Iniciando TelaCardapio");
    }

    @FXML
    private GridPane gridCardapio;

    @FXML
    private ImageView logoView;

}
