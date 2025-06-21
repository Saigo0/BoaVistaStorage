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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaLogin implements Initializable {

    private EntityManager entityManager;
    private AtendenteService atendenteService;
    private GerenteService gerenteService;

    @FXML private Button btnLogin;
    @FXML private PasswordField campoSenha;
    @FXML private TextField campoUser;
    @FXML private ImageView logoView;
    @FXML private Label txtLoginInvalido;
    @FXML private Label txtSenhaInvalida;

    /* --------------------------------------------------------------------- */
    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
        atendenteService = new AtendenteService(new AtendenteRepository(em));
        gerenteService   = new GerenteService(new GerenteRepository(em));
    }

    /* --------------------------------------------------------------------- */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarEventos();
    }

    private void configurarEventos() {
        btnLogin.setOnAction(e -> realizarLogin());

        campoUser.setOnKeyPressed(e -> txtLoginInvalido.setVisible(false));
        campoSenha.setOnKeyPressed(e -> txtSenhaInvalida.setVisible(false));

        txtLoginInvalido.setVisible(false);
        txtSenhaInvalida.setVisible(false);
    }

    /* --------------------------------------------------------------------- */
    private void realizarLogin() {
        String usuario = campoUser.getText();
        String senha   = campoSenha.getText();

        if (usuario == null || usuario.trim().isEmpty()) {
            txtLoginInvalido.setText("Informe um login válido");
            txtLoginInvalido.setVisible(true);
            return;
        }
        if (senha == null || senha.trim().isEmpty()) {
            txtSenhaInvalida.setText("Informe uma senha válida");
            txtSenhaInvalida.setVisible(true);
            return;
        }

        try {
            if (atendenteService.findByLoginAndSenha(usuario, senha) != null) {
                mudarCena("/com/ibdev/view/tela-principal-atendente.fxml", "Tela Principal Atendente");
                return;
            }
            if (gerenteService.findByLoginAndSenha(usuario, senha) != null) {
                mudarCena("/com/ibdev/view/tela-principal-gerente.fxml", "Tela Principal Gerente");
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();   // pode trocar por um logger
        }

        txtLoginInvalido.setText("Não foi possível realizar o login");
        txtSenhaInvalida.setText("Não foi possível realizar o login");
        txtLoginInvalido.setVisible(true);
        txtSenhaInvalida.setVisible(true);
    }

    private void mudarCena(String fxml, String titulo) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        SceneManager.mudarCena(stage, fxml, titulo);
    }
}
