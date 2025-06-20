package com.ibdev.boavistastorage.controller;

import jakarta.persistence.EntityManager;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaPrincipalGerente implements Initializable {
    private EntityManager entityManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
