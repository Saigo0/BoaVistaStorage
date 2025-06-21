package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.repository.CardapioRepository;
import com.ibdev.boavistastorage.service.CardapioService;
import com.ibdev.boavistastorage.main.SceneManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;

import java.net.URL;
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
    
    private CardapioService cardapioService;
    private Cardapio cardapioAtual;
    private TelaCardapio telaCardapioController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando TelaAdicionarProdutoCardapio");
        Platform.runLater(this::configurarEventos);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cardapioService = new CardapioService(new CardapioRepository(entityManager));
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapioAtual = cardapio;
        if (cardapioAtual != null) {
            System.out.println("Cardápio atual: " + cardapioAtual.getIdCardapio());
        } else {
            System.out.println("Nenhum cardápio foi definido.");
        }
    }

    public void setTelaCardapioController(TelaCardapio controller) {
        this.telaCardapioController = controller;
    }

    public void configurarEventos() {
        btnVoltar.setOnAction(event -> {
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();
        });

        btnAdicionarProduto.setOnAction(event -> {
            String nome = nomeTextField.getText().trim();
            String descricao = descricaoTextArea.getText().trim(); // Pega a descrição
            String precoStr = precoTextField.getText().trim();

            if (nome.isEmpty() || descricao.isEmpty() || precoStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Por favor, preencha todos os campos.");
                return;
            }

            double preco;
            try {
                preco = Double.parseDouble(precoStr.replace(",", ".")); // Lida com vírgula
                if (preco <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Erro de Validação", "O preço deve ser um valor positivo.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Preço inválido. Por favor, insira um número válido.");
                return;
            }

            if (cardapioAtual == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Cardápio não foi carregado corretamente.");
                return;
            }

            Produto novoProduto = new Vendavel();
            novoProduto.setNome(nome);
            novoProduto.setPrecoCusto(preco);

            try {
                entityManager.getTransaction().begin();
                novoProduto.setCardapio(cardapioAtual);
                entityManager.persist(novoProduto);
                cardapioAtual.addProdutosDisponiveis(novoProduto);
                entityManager.merge(cardapioAtual);
                entityManager.getTransaction().commit();

                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Produto adicionado com sucesso!");

                nomeTextField.clear();
                descricaoTextArea.clear();
                precoTextField.clear();

                Stage stage = (Stage) btnAdicionarProduto.getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                showAlert(Alert.AlertType.ERROR, "Erro ao Adicionar Produto", "Erro: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}