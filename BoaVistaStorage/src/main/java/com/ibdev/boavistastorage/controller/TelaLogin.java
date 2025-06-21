package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Atendente;
import com.ibdev.boavistastorage.entity.Funcionario;
import com.ibdev.boavistastorage.entity.Gerente;
import com.ibdev.boavistastorage.main.SceneManager;
import com.ibdev.boavistastorage.repository.AtendenteRepository;
import com.ibdev.boavistastorage.repository.GerenteRepository;
import com.ibdev.boavistastorage.service.AtendenteService;
import com.ibdev.boavistastorage.service.GerenteService;
import com.ibdev.boavistastorage.service.LoginService;
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
    private LoginService loginService;

    @FXML
    private Button btnLogin;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private TextField campoUser;
    @FXML
    private ImageView logoView;
    @FXML
    private Label txtLoginInvalido;
    @FXML
    private Label txtSenhaInvalida;

    public void setEntityManager(EntityManager em) {
        System.out.println("setEntityManager chamado no controller: " + this);
        this.entityManager = em;
        atendenteService = new AtendenteService(new AtendenteRepository(em));
        gerenteService = new GerenteService(new GerenteRepository(em));
        loginService = new LoginService(atendenteService, gerenteService);
    }

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

    private void realizarLogin() {
        String usuario = campoUser.getText();
        String senha = campoSenha.getText();

        if (usuario == null || usuario.trim().isEmpty()) {
            txtLoginInvalido.setText("O campo usuário é obrigatório");
            txtLoginInvalido.setVisible(true);
            return;
        }
        if (senha == null || senha.trim().isEmpty()) {
            txtSenhaInvalida.setText("O campo senha é obrigatório");
            txtSenhaInvalida.setVisible(true);
            return;
        }

        txtLoginInvalido.setVisible(false);
        txtSenhaInvalida.setVisible(false);

        try {
            Funcionario funcionario = loginService.atutenticar(usuario, senha);

            if (funcionario instanceof Gerente) {
                Gerente gerente = (Gerente) funcionario;
                System.out.println("Gerente logado: " + gerente.getNome());
                SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-principal-gerente.fxml", "Tela Principal Gerente");

            } else if (funcionario instanceof Atendente) {
                Atendente atendente = (Atendente) funcionario;
                System.out.println("Login como ATENDENTE bem-sucedido. Usuário de nome: " + atendente.getNome());
                SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-principal-atendente.fxml", "Tela Principal Atendente");
            }

            else {
                txtLoginInvalido.setText("Usuário ou senha inválidos");
                txtLoginInvalido.setVisible(true);
                txtSenhaInvalida.setText("Usuário ou senha inválidos");
                txtSenhaInvalida.setVisible(true);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            txtLoginInvalido.setText("Ocorreu um erro inesperado no sistema. Tente novamente mais tarde.");
            txtSenhaInvalida.setText("Ocorreu um erro inesperado no sistema. Tente novamente mais tarde.");
        }
    }
}
