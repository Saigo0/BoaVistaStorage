package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.repository.CardapioRepository;
import com.ibdev.boavistastorage.service.CardapioService;
import com.ibdev.boavistastorage.main.SceneManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane; // Adicionado para o FXML
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.net.URL;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::configurarEventos);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cardapioService = new CardapioService(new CardapioRepository(entityManager));

        if (this.cardapio == null) {
            try {
                this.cardapio = cardapioService.findCardapioById(1L);
                if (this.cardapio == null) {
                    System.out.println("Cardapio com ID 1 não encontrado. Criando um novo...");
                    this.cardapio = new Cardapio();

                    entityManager.getTransaction().begin();
                    entityManager.persist(this.cardapio);
                    entityManager.getTransaction().commit();
                    System.out.println("Novo cardápio criado com ID: " + this.cardapio.getIdCardapio());
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar ou criar cardápio: " + e.getMessage());
            }
        }
        carregarProdutosNoCardapio();
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
        if (this.entityManager != null) {
            carregarProdutosNoCardapio();
        }
    }


    public void configurarEventos() {
        btnInicio.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-principal-atendente.fxml", "Boa Vista Storage - Tela Principal");
        });

        btnLogout.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-login.fxml", "Login - Boa Vista Storage"
            );
        });

        btnCadastrarCardapio.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ibdev/view/tela-adicionar-produto-cardapio.fxml"));
                Parent root = loader.load();

                TelaAdicionarProdutoCardapio controller = loader.getController();
                controller.setEntityManager(entityManager);
                controller.setCardapio(cardapio);
                controller.setTelaCardapioController(this);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Adicionar Produto ao Cardápio");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                carregarProdutosNoCardapio();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void carregarProdutosNoCardapio() {
        System.out.println("Carregando produtos no cardápio...");
        if (cardapio == null || cardapio.getProdutosDisponiveis() == null) {
            System.out.println("Cardapio ou lista de produtos é nula. Não é possível carregar.");
            return;
        }

        gridCardapio.getChildren().clear();
        int col = 0;
        int row = 0;
        final int MAX_COLUMNS = 4;

        for (Produto produto : cardapio.getProdutosDisponiveis()) {
            VBox produtoVBox = criarVBoxProduto(produto);

            gridCardapio.add(produtoVBox, col, row);

            col++;
            if (col == MAX_COLUMNS) {
                col = 0;
                row++;
            }
        }
        System.out.println("Produtos carregados: " + cardapio.getProdutosDisponiveis().size());
    }

    private VBox criarVBoxProduto(Produto produto) {
        VBox vbox = new VBox();
        vbox.setSpacing(8);
        vbox.setStyle("-fx-background-color:#ffffff; -fx-background-radius:8; -fx-padding:15;");
        vbox.setAlignment(javafx.geometry.Pos.TOP_LEFT);

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(100, 100);
        stackPane.setStyle("-fx-background-color:#d9d9d9; -fx-background-radius:4;");
        Label imgLabel = new Label("Sem Imagem");
        stackPane.getChildren().add(imgLabel);

        Label nomeLabel = new Label(produto.getNome());
        nomeLabel.setStyle("-fx-font-weight:bold;");

        String descricaoProduto = "Descrição não disponível";

        Label descricaoLabel = new Label(descricaoProduto);
        if (produto instanceof com.ibdev.boavistastorage.entity.Vendavel) {
            descricaoLabel.setText(descricaoProduto);
        } else if (produto instanceof com.ibdev.boavistastorage.entity.Produzido) {
            descricaoLabel.setText(descricaoProduto);
        } else {
            descricaoLabel.setText("Descrição não disponível");
        }

        descricaoLabel.setWrapText(true);
        descricaoLabel.setMaxWidth(150);
        descricaoLabel.setStyle("-fx-font-size:11px;");

        Label precoLabel = new Label(String.format("R$ %.2f", produto.getPrecoCusto()));
        precoLabel.setStyle("-fx-text-fill:#c74921; -fx-font-weight:bold;");

        vbox.getChildren().addAll(stackPane, nomeLabel, descricaoLabel, precoLabel);
        return vbox;
    }
}