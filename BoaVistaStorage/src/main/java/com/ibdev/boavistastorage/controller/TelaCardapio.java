package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.entity.Produzido;
import com.ibdev.boavistastorage.repository.CardapioRepository;
import com.ibdev.boavistastorage.service.CardapioService;
import com.ibdev.boavistastorage.main.SceneManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private Button btnRemoverItemCardapio;

    @FXML
    private HBox btnVenda;

    @FXML
    private GridPane gridCardapio;

    @FXML
    private ImageView logoView;

    private Cardapio cardapio;
    private CardapioService cardapioService;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::configurarEventos);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cardapioService = new CardapioService(new CardapioRepository(entityManager));
        System.out.println("EntityManager setado na TelaCardapio.");

        carregarCardapioInicialmente();
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
        if (this.entityManager != null) {
            carregarProdutosNoCardapio(); // Recarrega se o EM já estiver setado
        }
    }

    private void carregarCardapioInicialmente() {
        gridCardapio.getChildren().clear();
        Label loadingLabel = new Label("Carregando cardápio...");
        ProgressIndicator progress = new ProgressIndicator();
        VBox loadingBox = new VBox(10, progress, loadingLabel);
        loadingBox.setAlignment(Pos.CENTER);
        gridCardapio.add(loadingBox, 0, 0, 4, 1);

        executor.execute(() -> {
            Cardapio loadedCardapio = null;
            try {
                loadedCardapio = cardapioService.findCardapioById(1L);
                if (loadedCardapio == null) {
                    System.out.println("Cardapio com ID 1 não encontrado. Tentando buscar o primeiro.");
                    loadedCardapio = cardapioService.findAllCardapios().stream().findFirst().orElse(null);
                    if (loadedCardapio == null) {
                        System.out.println("Nenhum cardápio existente. Criando um novo...");
                        loadedCardapio = new Cardapio();
                        entityManager.getTransaction().begin();
                        entityManager.persist(loadedCardapio);
                        entityManager.getTransaction().commit();
                        System.out.println("Novo cardápio criado com ID: " + loadedCardapio.getIdCardapio());
                    }
                }
            } catch (NoResultException e) {
                System.out.println("Cardapio com ID 1 não encontrado (NoResultException). Criando um novo...");
                try {
                    entityManager.getTransaction().begin();
                    loadedCardapio = new Cardapio();
                    entityManager.persist(loadedCardapio);
                    entityManager.getTransaction().commit();
                    System.out.println("Novo cardápio criado com ID: " + loadedCardapio.getIdCardapio());
                } catch (Exception ex) {
                    if (entityManager.getTransaction().isActive()) {
                        entityManager.getTransaction().rollback();
                    }
                    Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Erro de Banco de Dados", "Falha ao criar novo cardápio: " + ex.getMessage()));
                    System.err.println("Erro ao criar novo cardápio: " + ex.getMessage());
                }
            } catch (Exception e) {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Erro de Carregamento", "Falha ao carregar cardápio: " + e.getMessage()));
                System.err.println("Erro ao carregar cardápio: " + e.getMessage());
            }

            final Cardapio finalLoadedCardapio = loadedCardapio;
            Platform.runLater(() -> {
                if (finalLoadedCardapio != null) {
                    this.cardapio = finalLoadedCardapio;
                    carregarProdutosNoCardapio();
                } else {
                    gridCardapio.getChildren().clear();
                    gridCardapio.add(new Label("Nenhum cardápio disponível."), 0, 0);
                }
            });
        });
    }

    public void configurarEventos() {
        btnInicio.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-principal-atendente.fxml", "Boa Vista Storage - Tela Principal", entityManager);
        });

        btnLogout.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-login.fxml", "Login - Boa Vista Storage", entityManager
            );
        });

        btnCadastrarCardapio.setOnMouseClicked(event -> {
            if (cardapio == null || cardapio.getIdCardapio() == null) {
                showAlert(Alert.AlertType.WARNING, "Atenção", "Cardápio não carregado ou não existe. Crie um cardápio primeiro.");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ibdev/view/tela-adicionar-produto-cardapio.fxml"));
                Parent root = loader.load();

                TelaAdicionarProdutoCardapio controller = loader.getController();
                controller.setEntityManager(entityManager);
                controller.setCardapio(cardapio); // Passa a instância do Cardapio
                controller.setTelaCardapioController(this); // Referência para este controller

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Adicionar Produto ao Cardápio");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                carregarProdutosNoCardapio();

            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Carregamento", "Não foi possível carregar a tela de adicionar produto: " + e.getMessage());
                e.printStackTrace();
            }
        });

        btnRemoverItemCardapio.setOnMouseClicked(event -> {
            if (cardapio == null || cardapio.getIdCardapio() == null) {
                showAlert(Alert.AlertType.WARNING, "Atenção", "Cardápio não carregado ou não existe. Crie um cardápio primeiro.");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ibdev/view/tela-excluir-produto-cardapio.fxml"));
                Parent root = loader.load();

                TelaExcluirProdutoCardapio controller = loader.getController();
                controller.setEntityManager(entityManager);
                controller.setCardapio(cardapio);
                controller.setTelaCardapioController(this);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Excluir Produto do Cardápio");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Carregamento", "Não foi possível carregar a tela de exclusão de produto: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void carregarProdutosNoCardapio() {
        System.out.println("Recarregando produtos no cardápio na UI...");
        if (cardapio == null) {
            System.out.println("Objeto Cardapio é nulo. Não é possível carregar produtos.");
            gridCardapio.getChildren().clear();
            gridCardapio.add(new Label("Erro: Cardápio não disponível."), 0, 0);
            return;
        }
        try {
            entityManager.refresh(cardapio);
            System.out.println("Cardápio atualizado do banco de dados para recarregar produtos.");
        } catch (Exception e) {
            System.err.println("Erro ao refrescar cardápio do banco de dados: " + e.getMessage());
        }

        List<Produto> produtosDisponiveis = cardapio.getProdutosDisponiveis();
        if (produtosDisponiveis == null || produtosDisponiveis.isEmpty()) {
            System.out.println("Nenhum produto disponível no cardápio.");
            gridCardapio.getChildren().clear();
            Label noProductsLabel = new Label("Nenhum produto cadastrado no cardápio.");
            noProductsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
            gridCardapio.add(noProductsLabel, 0, 0, 4, 1); // Centraliza a mensagem
            return;
        }

        gridCardapio.getChildren().clear();
        int col = 0;
        int row = 0;
        final int MAX_COLUMNS = 4;

        for (Produto produto : produtosDisponiveis) {
            VBox produtoVBox = criarVBoxProduto(produto);

            gridCardapio.add(produtoVBox, col, row);

            col++;
            if (col == MAX_COLUMNS) {
                col = 0;
                row++;
            }
        }
        System.out.println("Produtos carregados: " + produtosDisponiveis.size());
    }

    private VBox criarVBoxProduto(Produto produto) {
        VBox vbox = new VBox();
        vbox.setSpacing(8);
        vbox.setPrefHeight(250);
        vbox.setStyle("-fx-background-color:#ffffff; -fx-background-radius:8; -fx-padding:15;");
        vbox.setAlignment(javafx.geometry.Pos.TOP_LEFT);

        // StackPane para a imagem (ou "Sem Imagem")
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(100, 100);
        stackPane.setStyle("-fx-background-color:#d9d9d9; -fx-background-radius:4;");
        Label imgLabel = new Label("Sem Imagem");
        StackPane.setAlignment(imgLabel, Pos.CENTER); // Centraliza o texto "Sem Imagem"
        stackPane.getChildren().add(imgLabel);

        Label nomeLabel = new Label(produto.getNome());
        nomeLabel.setStyle("-fx-font-weight:bold; -fx-font-size:14px;");

        Label descricaoLabel;
        if (produto instanceof Produzido) {
            String descricaoTexto = ((Produzido) produto).getDescricao();
            descricaoLabel = new Label(descricaoTexto != null && !descricaoTexto.isEmpty() ? descricaoTexto : "Sem descrição.");
        } else {
            descricaoLabel = new Label("N/A");
        }
        descricaoLabel.setWrapText(true);
        descricaoLabel.setMaxWidth(150); // Mantém uma largura máxima para quebra de texto
        descricaoLabel.setStyle("-fx-font-size:11px; -fx-text-fill:#666;"); // Cor de texto mais suave

        if (produto instanceof Produzido) {
            Label precoLabel = new Label(String.format("R$ %.2f", ((Produzido) produto).getPrecoVenda()));
            precoLabel.setStyle("-fx-text-fill:#c74921; -fx-font-weight:bold; -fx-font-size:1.1em;");
            vbox.getChildren().addAll(stackPane, nomeLabel, descricaoLabel, precoLabel);
        }

        if (produto instanceof Vendavel) {
            Label precoLabel = new Label(String.format("R$ %.2f", ((Vendavel) produto).getPrecoVenda()));
            precoLabel.setStyle("-fx-text-fill:#c74921; -fx-font-weight:bold; -fx-font-size:1.1em;");
            vbox.getChildren().addAll(stackPane, nomeLabel, descricaoLabel, precoLabel);
        }
        return vbox;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Platform.runLater(() -> { // Garante que o alerta seja mostrado na thread JavaFX
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    // Certifique-se de fechar o ExecutorService quando a aplicação parar
    public void closeExecutor() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
            System.out.println("ExecutorService da TelaCardapio encerrado.");
        }
    }
}