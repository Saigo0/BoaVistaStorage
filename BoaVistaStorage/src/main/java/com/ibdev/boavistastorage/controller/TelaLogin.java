package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.main.SceneManager;
import com.ibdev.boavistastorage.repository.AtendenteRepository;
import com.ibdev.boavistastorage.repository.GerenteRepository;
import com.ibdev.boavistastorage.service.AtendenteService;
import com.ibdev.boavistastorage.service.GerenteService;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaLogin implements Initializable {
    private EntityManager entityManager;
    private AtendenteRepository atendenteRepository;
    private GerenteRepository gerenteRepository;
    private GerenteService gerenteService;
    private AtendenteService atendenteService;

    public TelaLogin() {
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.atendenteRepository = new AtendenteRepository(entityManager);
        this.gerenteRepository = new GerenteRepository(entityManager);
        this.gerenteService = new GerenteService(gerenteRepository);
        this.atendenteService = new AtendenteService(atendenteRepository);
    }

    @FXML
    private Button btnLogin;

    @FXML
    private TextField campoUser;

    @FXML
    private TextField campoSenha;

    @FXML
    private Label txtLoginInvalido;

    @FXML
    private Label txtSenhaInvalida;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Iniciando TelaLogin");
        configurarEventos();
    }

    private void configurarEventos() {
        btnLogin.setOnAction(event -> {
            realizarLogin();
        });

        campoUser.setOnKeyPressed(event -> {
            txtLoginInvalido.setText("");
        });

        campoSenha.setOnKeyPressed(event -> {
            txtSenhaInvalida.setText("");
        });

        txtLoginInvalido.setText("");
        txtLoginInvalido.setVisible(false);
        txtSenhaInvalida.setText("");
        txtSenhaInvalida.setVisible(false);

    }

    private void realizarLogin() {
        String usuario = campoUser.getText();
        String senha = campoSenha.getText();

        if (usuario.trim().isEmpty() || usuario == null) {
            txtLoginInvalido.setText("Informe um login válido");
            txtLoginInvalido.setVisible(true);
            txtSenhaInvalida.setText("");
            return;
        }
        txtLoginInvalido.setText("");
        txtSenhaInvalida.setText("");

        if (senha.trim().isEmpty() || senha == null) {
            txtSenhaInvalida.setText("Informe uma senha válida");
            txtSenhaInvalida.setVisible(true);
            return;
        }
        txtSenhaInvalida.setText("");
        txtLoginInvalido.setText("");

        try {
            if (atendenteService.findByLoginAndSenha(usuario, senha) != null) {
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                SceneManager.mudarCena(stage, "/com/ibdev/view/tela-principal-atendente.fxml", "Tela Principal Atendente");
                return;
            }
        } catch (Exception e) {
        }

        try {
            if (gerenteService.findByLoginAndSenha(usuario, senha) != null) {
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                SceneManager.mudarCena(stage, "/com/ibdev/view/tela-principal-gerente.fxml", "Tela Principal Gerente");
                return;
            }
        } catch (Exception e) {
        }

        txtLoginInvalido.setText("Não foi possível realizar o login");
        txtLoginInvalido.setVisible(true);
        txtSenhaInvalida.setText("Não foi possível realizar o login");
        txtSenhaInvalida.setVisible(true);
    }
}

