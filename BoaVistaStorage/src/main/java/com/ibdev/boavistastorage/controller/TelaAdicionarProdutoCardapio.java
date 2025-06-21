package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.main.SceneManager;
import com.ibdev.boavistastorage.repository.CardapioRepository;
import com.ibdev.boavistastorage.service.CardapioService;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TelaAdicionarProdutoCardapio implements Initializable {
    private EntityManager entityManager;

    @FXML
    private Button btnAdicionarProduto;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextArea descricaoTextArea;

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField precoTextField;

    @FXML
    private TextField txtIdCardapio;


    private CardapioService cardapioService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando TelaCardapio com EntityManager: " + entityManager);

        Platform.runLater(() -> {
            configurarEventos();
        });
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cardapioService = new CardapioService(new CardapioRepository(entityManager));
    }

    public void configurarEventos() {
        btnVoltar.setOnAction(event -> {
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-cardapio.fxml", "TelaCardapio");
        });

        btnAdicionarProduto.setOnAction(event -> {
            Produto produto = new Vendavel();
            String nome = nomeTextField.getText();
            String idCardapio = txtIdCardapio.getText();
            String precoStr = precoTextField.getText();

            if (nome.isEmpty() || precoStr.isEmpty()) {
                System.out.println("Por favor, preencha todos os campos.");
                return;
            }

            double preco;
            try {
                preco = Double.parseDouble(precoStr);
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido. Por favor, insira um número válido.");
                return;
            }

            Long idCardapioLong;
            try {
                idCardapioLong = Long.parseLong(idCardapio);
            } catch (NumberFormatException e) {
                System.out.println("ID do cardápio inválido. Por favor, insira um número válido.");
                return;
            }

            produto.setNome(nome);
            produto.setPrecoCusto(preco);

            cardapioService.addProdutoAoCardapio(idCardapioLong, produto);

            nomeTextField.clear();
            descricaoTextArea.clear();
            precoTextField.clear();

            System.out.println("Produto adicionado com sucesso!");
        });
    }
}
