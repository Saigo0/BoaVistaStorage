package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.ItemPedido;
import com.ibdev.boavistastorage.entity.Pedido;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.entity.Produzido;

import com.ibdev.boavistastorage.service.ProdutoService;
import com.ibdev.boavistastorage.service.PedidoService;
import com.ibdev.boavistastorage.repository.ProdutoRepository;
import com.ibdev.boavistastorage.repository.PedidoRepository;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressIndicator;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors; // Import adicionado para Stream API

public class TelaPedidoAtendente implements Initializable {

    @FXML private FlowPane flowPaneProdutos;
    @FXML private VBox vBoxItensPedido;
    @FXML private Label lblPedidoNumero;
    @FXML private Label lblTotalPedido;
    @FXML private Button btnProsseguir;
    @FXML private ProgressIndicator progressIndicator;

    @FXML private Button handleInicio;
    @FXML private Button handlePedidos;
    @FXML private Button handleVenda;
    @FXML private Button handleCardapio;
    @FXML private Button handleCliente;

    private EntityManager entityManager;
    private ProdutoService produtoService;
    private PedidoService pedidoService;

    private Pedido pedidoAtual;
    private Map<Long, ItemPedido> itensNoPedido;

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itensNoPedido = new HashMap<>();
        lblTotalPedido.setText(currencyFormat.format(0.00));

        btnProsseguir.setOnAction(event -> handleProsseguirPedido());

        if (handleInicio != null) handleInicio.setOnAction(e -> System.out.println("Navegar para Início (ação não implementada)"));
        if (handlePedidos != null) handlePedidos.setOnAction(e -> System.out.println("Tela de Pedidos (atual)"));
        if (handleVenda != null) handleVenda.setOnAction(e -> System.out.println("Navegar para Venda (ação não implementada)"));
        if (handleCardapio != null) handleCardapio.setOnAction(e -> System.out.println("Navegar para Cardápio (ação não implementada)"));
        if (handleCliente != null) handleCliente.setOnAction(e -> System.out.println("Navegar para Cliente (ação não implementada)"));

        if (progressIndicator != null) {
            progressIndicator.setVisible(false);
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.produtoService = new ProdutoService(new ProdutoRepository(entityManager));
        this.pedidoService = new PedidoService(new PedidoRepository(entityManager));

        Platform.runLater(() -> {
            iniciarNovoPedido();
            carregarProdutosNoCardapio();
        });
    }

    private void iniciarNovoPedido() {
        this.pedidoAtual = new Pedido();
        lblPedidoNumero.setText("Pedido #NOVO");
        itensNoPedido.clear();
        vBoxItensPedido.getChildren().clear();
        atualizarTotalPedido();
    }

    private void carregarProdutosNoCardapio() {
        flowPaneProdutos.getChildren().clear();

        if (progressIndicator != null) {
            progressIndicator.setVisible(true);
        }

        CompletableFuture.supplyAsync(() -> {
            try {
                // Filtra os produtos para incluir apenas aqueles que têm um cardapioId associado
                return produtoService.buscarTodosProdutosDisponiveis().stream()
                        .filter(produto -> produto.getCardapio() != null) // Assumindo que Produto tem getCardapioId()
                        .collect(Collectors.toList());
            } catch (Exception e) {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Produtos", "Não foi possível carregar os produtos do cardápio: " + e.getMessage()));
                e.printStackTrace();
                return Collections.emptyList();
            }
        }, executorService).thenAccept(produtos -> {
            Platform.runLater(() -> {
                if (progressIndicator != null) {
                    progressIndicator.setVisible(false);
                }

                if (produtos.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "Informação", "Nenhum produto disponível no cardápio.");
                    System.out.println("Nenhum produto encontrado no banco de dados com cardápio associado.");
                } else {
                    System.out.println(produtos.size() + " produtos encontrados no cardápio. Carregando cards...");
                    produtos.stream().map(produto -> (Produto) produto).forEach(this::adicionarCardProduto);
                }
            });
        });
    }

    private void adicionarCardProduto(Produto produto) {
        try {
            // Caminho ajustado: Usando '/' para separadores de pacote/diretório
            String fxmlPath = "/com/ibdev/view/card-produto-pedido.fxml";
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                System.err.println("ERRO: FXML do Card de Produto NÃO ENCONTRADO! Caminho tentado: " + fxmlPath);
                throw new IOException("Caminho do FXML 'card-produto-pedido.fxml' inválido ou arquivo não encontrado.");
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            VBox cardProduto = loader.load();

            Label lblNome = (Label) cardProduto.lookup("#lblNomeProduto");
            Label lblDescricao = (Label) cardProduto.lookup("#lblDescricaoProduto");
            Label lblPreco = (Label) cardProduto.lookup("#lblPrecoProduto");
            Button btnAdicionar = (Button) cardProduto.lookup("#btnAdicionarAoPedido");

            lblNome.setText(produto.getNome());

            double precoExibicao = 0.0;
            if (produto instanceof Vendavel) {
                precoExibicao = ((Vendavel) produto).getPrecoVenda();
            } else if (produto instanceof Produzido) {
                precoExibicao = produto.getPrecoCusto();
            }
            lblPreco.setText(currencyFormat.format(precoExibicao));

            btnAdicionar.setOnAction(event -> adicionarItemAoPedido(produto));

            flowPaneProdutos.getChildren().add(cardProduto);

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Card", "Erro ao carregar card do produto '" + produto.getNome() + "': " + e.getMessage());
            e.printStackTrace();
        } catch (ClassCastException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Casting", "Erro de tipo ao configurar card do produto '" + produto.getNome() + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void adicionarItemAoPedido(Produto produto) {
        ItemPedido item = itensNoPedido.get(produto.getId());

        if (item == null) {
            item = new ItemPedido(1, produto);
            itensNoPedido.put(produto.getId(), item);
            pedidoAtual.getItensList().add(item);
            adicionarMiniCardItemPedido(item);
        } else {
            item.setQuantidade(item.getQuantidade() + 1);
            atualizarMiniCardQuantidade(item);
        }
        atualizarTotalPedido();
    }

    private void adicionarMiniCardItemPedido(ItemPedido item) {
        try {
            // Caminho ajustado: Usando '/' para separadores de pacote/diretório
            String fxmlPath = "/com/ibdev/view/mini-card-item-pedido.fxml";
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                System.err.println("ERRO: FXML do Mini Card de Item de Pedido NÃO ENCONTRADO! Caminho tentado: " + fxmlPath);
                throw new IOException("Caminho do FXML 'mini-card-item-pedido.fxml' inválido ou arquivo não encontrado.");
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            HBox miniCard = loader.load();

            Label lblNome = (Label) miniCard.lookup("#lblNomeItemPedido");
            Label lblPrecoUnitario = (Label) miniCard.lookup("#lblPrecoUnitario");
            Label lblQuantidade = (Label) miniCard.lookup("#lblQuantidadeItemPedido");
            Button btnDiminuir = (Button) miniCard.lookup("#btnDiminuirQuantidade");
            Button btnAumentar = (Button) miniCard.lookup("#btnAumentarQuantidade");

            lblNome.setText(item.getProduto().getNome());

            double precoUnit = 0;
            if (item.getProduto() instanceof Vendavel) {
                precoUnit = ((Vendavel) item.getProduto()).getPrecoVenda();
            } else if (item.getProduto() instanceof Produzido) {
                precoUnit = item.getProduto().getPrecoCusto();
            }
            lblPrecoUnitario.setText(currencyFormat.format(precoUnit) + "/un");
            lblQuantidade.setText(String.valueOf(item.getQuantidade()));

            btnDiminuir.setOnAction(e -> alterarQuantidadeItem(item, -1));
            btnAumentar.setOnAction(e -> alterarQuantidadeItem(item, 1));

            item.setUserData(miniCard);
            miniCard.setUserData(lblQuantidade);

            vBoxItensPedido.getChildren().add(miniCard);

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Mini Card", "Erro ao carregar mini card para o item '" + item.getProduto().getNome() + "': " + e.getMessage());
            e.printStackTrace();
        } catch (ClassCastException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Casting", "Erro de tipo ao configurar mini card do item '" + item.getProduto().getNome() + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void alterarQuantidadeItem(ItemPedido item, int delta) {
        Platform.runLater(() -> {
            int novaQuantidade = item.getQuantidade() + delta;

            if (novaQuantidade <= 0) {
                itensNoPedido.remove(item.getProduto().getId());
                pedidoAtual.getItensList().remove(item);

                HBox miniCard = (HBox) item.getUserData();
                if (miniCard != null) {
                    vBoxItensPedido.getChildren().remove(miniCard);
                }
            } else {
                item.setQuantidade(novaQuantidade);
                Label lblQuantidade = (Label) ((HBox) item.getUserData()).getUserData();
                if (lblQuantidade != null) {
                    lblQuantidade.setText(String.valueOf(novaQuantidade));
                }
            }
            atualizarTotalPedido();
        });
    }

    private void atualizarMiniCardQuantidade(ItemPedido item) {
        Platform.runLater(() -> {
            HBox miniCard = (HBox) item.getUserData();
            if (miniCard != null) {
                Label lblQuantidade = (Label) miniCard.getUserData();
                if (lblQuantidade != null) {
                    lblQuantidade.setText(String.valueOf(item.getQuantidade()));
                }
            }
        });
    }

    private void atualizarTotalPedido() {
        Platform.runLater(() -> {
            double total = 0.0;
            for (ItemPedido item : itensNoPedido.values()) {
                double precoUnit = 0;
                if (item.getProduto() instanceof Vendavel) {
                    precoUnit = ((Vendavel) item.getProduto()).getPrecoVenda();
                } else if (item.getProduto() instanceof Produzido) {
                    precoUnit = item.getProduto().getPrecoCusto();
                }
                total += item.getQuantidade() * precoUnit;
            }
            lblTotalPedido.setText(currencyFormat.format(total));
        });
    }

    private void handleProsseguirPedido() {
        if (itensNoPedido.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Pedido Vazio", "Adicione itens ao pedido antes de prosseguir.");
            return;
        }

        try {
            entityManager.getTransaction().begin();

            for (ItemPedido item : pedidoAtual.getItensList()) {
                item.setPedido(pedidoAtual);
            }

            pedidoService.createPedido(pedidoAtual);

            entityManager.getTransaction().commit();
            showAlert(Alert.AlertType.INFORMATION, "Pedido Finalizado", "Pedido #" + pedidoAtual.getIdPedido() + " registrado com sucesso!");

            iniciarNovoPedido();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            showAlert(Alert.AlertType.ERROR, "Erro ao Finalizar Pedido", "Não foi possível finalizar o pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @FXML private void handleInicio() {}
    @FXML private void handlePedidos() {}
    @FXML private void handleVenda() {}
    @FXML private void handleCardapio() {}
    @FXML private void handleCliente() {}
}