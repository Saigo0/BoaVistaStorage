package com.ibdev.boavistastorage.controller;


import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.main.SceneManager;
import com.ibdev.boavistastorage.repository.CardapioRepository;
import com.ibdev.boavistastorage.service.CardapioService;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TelaCardapio implements Initializable {
    private EntityManager entityManager;

    @FXML
    private Button btnCadastrarCardapio;

    @FXML
    private HBox btnCardapio;

    @FXML
    private HBox btnCliente;

    @FXML
    private HBox btnInicio;

    @FXML
    private HBox btnLogout;

    @FXML
    private HBox btnPedidos;

    @FXML
    private HBox btnVenda;

    @FXML
    private GridPane gridCardapio;

    @FXML
    private Label insumos;

    @FXML
    private Label itemNome;

    @FXML
    private ImageView logoView;

    @FXML
    private Label precoVenda;

    @FXML
    private VBox vboxCardapio;

    private Cardapio cardapio;

    private CardapioService cardapioService;

    private List<Produto> produtos;

    private List<Produto> armazenaProdutosCardapio;

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
        this.produtos = new ArrayList<>();
        this.armazenaProdutosCardapio = cardapioService.findCardapioById(cardapio.getIdCardapio()).getProdutosDisponiveis();
    }

    public void configurarEventos() {
        btnInicio.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnInicio.getScene().getWindow();
            SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-principal-atendente.fxml", "Boa Vista Storage - Tela Principal");
        });

        btnLogout.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-login.fxml", "Login - Boa Vista Storage");
        });

        btnCadastrarCardapio.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnCadastrarCardapio.getScene().getWindow();
            SceneManager.mudarCenaMaximizada("/com/ibdev/view/tela-adicionar-produto-cardapio.fxml", "Cadastro de Pedido - Boa Vista Storage");
        });
    }

    private void carregarProdutosNoCardapio() {
        gridCardapio.getChildren().clear(); // Limpa o GridPane antes de adicionar novamente
        int col = 0;
        int row = 0;
        final int MAX_COLUMNS = 4; // Define o número máximo de colunas

        for (Produto produto : produtos) {
            VBox produtoVBox = criarVBoxProduto(produto);

            gridCardapio.add(produtoVBox, col, row);

            col++;
            if (col == MAX_COLUMNS) {
                col = 0;
                row++;
            }
        }
    }

    private VBox criarVBoxProduto(Produto produto) {
        VBox vbox = new VBox();
        vbox.setSpacing(5); // Espaçamento entre os elementos dentro da VBox
        vbox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-padding: 5px;");
        vbox.setAlignment(javafx.geometry.Pos.TOP_CENTER); // Alinha o conteúdo ao centro superior

        // StackPane para a imagem (ou "Sem Imagem")
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(100, 100); // Tamanho fixo para o espaço da imagem
        stackPane.setStyle("-fx-background-color: lightgray;"); // Fundo cinza para "Sem Imagem"
        Label imgLabel = new Label("Sem Imagem");
        stackPane.getChildren().add(imgLabel);

        Label nomeLabel = new Label(produto.getNome());
        nomeLabel.setStyle("-fx-font-weight: bold;"); // Negrito para o nome

        Label descricaoLabel = new Label("Pastel de frango, vulgo nataniel");
        descricaoLabel.setWrapText(true); // Quebra de texto para descrições longas
        descricaoLabel.setMaxWidth(150); // Largura máxima para a descrição

        Label precoLabel = new Label(String.format("R$ %.2f", produto.getPrecoCusto()));
        precoLabel.setStyle("-fx-font-size: 1.1em; -fx-text-fill: green;"); // Preço maior e verde

        vbox.getChildren().addAll(stackPane, nomeLabel, descricaoLabel, precoLabel);
        return vbox;
    }
}
