package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.EstoqueItem;
import com.ibdev.boavistastorage.entity.Insumo;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.entity.StatusEstoque;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.repository.InsumoRepository;
import com.ibdev.boavistastorage.repository.VendavelRepository;
import com.ibdev.boavistastorage.service.InsumoService;
import com.ibdev.boavistastorage.service.VendavelService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaEstoqueGerente implements Initializable {

    @FXML
    private Button btnAdicionar;
    @FXML
    private TableView<EstoqueItem> tabelaEstoque;
    @FXML
    private TableColumn<EstoqueItem, Long> colCodigo;
    @FXML
    private TableColumn<EstoqueItem, String> colProduto;
    @FXML
    private TableColumn<EstoqueItem, String> colQtde;
    @FXML
    private TableColumn<EstoqueItem, Double> colPreco;
    @FXML
    private TableColumn<EstoqueItem, StatusEstoque> colStatus;
    @FXML
    private TableColumn<EstoqueItem, Void> colAcoes;

    private ObservableList<EstoqueItem> listaItensEstoque = FXCollections.observableArrayList();

    private EntityManager entityManager;
    private InsumoService insumoService;
    private VendavelService vendavelService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando TelaEstoqueGerente");
        configurarTabela();
        configurarEventos();
        tabelaEstoque.setItems(listaItensEstoque);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.insumoService = new InsumoService(new InsumoRepository(entityManager));
        this.vendavelService = new VendavelService(new VendavelRepository(entityManager));

        System.out.println("EntityManager setado na TelaEstoqueGerente. Tentando carregar dados...");
        Platform.runLater(this::carregarDadosEstoque);
    }

    private void configurarTabela() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQtde.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoCusto"));

        colPreco.setCellFactory(tc -> new TableCell<EstoqueItem, Double>() {
            @Override
            protected void updateItem(Double preco, boolean empty) {
                super.updateItem(preco, empty);
                if (empty || preco == null) {
                    setText(null);
                } else {
                    setText(String.format("R$ %.2f", preco));
                }
            }
        });

        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusEstoque"));
        colStatus.setCellFactory(tc -> new TableCell<EstoqueItem, StatusEstoque>() {
            @Override
            protected void updateItem(StatusEstoque status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status.name());
                    switch (status) {
                        case BOM:
                            setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                            break;
                        case RAZOAVEL:
                            setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                            break;
                        case ZERADO:
                            setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("");
                    }
                }
            }
        });

        Callback<TableColumn<EstoqueItem, Void>, TableCell<EstoqueItem, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<EstoqueItem, Void> call(final TableColumn<EstoqueItem, Void> param) {
                final TableCell<EstoqueItem, Void> cell = new TableCell<>() {

                    private final Button btnUpdate = new Button("Editar");
                    private final Button btnDelete = new Button("Excluir");

                    {
                        btnUpdate.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 5;");
                        btnDelete.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 5;");

                        btnUpdate.setOnAction(event -> {
                            EstoqueItem item = getTableView().getItems().get(getIndex());
                            handleUpdateItem(item.getProdutoOriginal());
                        });

                        btnDelete.setOnAction(event -> {
                            EstoqueItem item = getTableView().getItems().get(getIndex());
                            handleDeleteItem(item.getProdutoOriginal());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(5, btnUpdate, btnDelete);
                            hbox.setAlignment(Pos.CENTER);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        };
        colAcoes.setCellFactory(cellFactory);
    }

    private void carregarDadosEstoque() {
        if (entityManager == null || insumoService == null || vendavelService == null) {
            showAlert(Alert.AlertType.ERROR, "Erro de Inicialização", "Serviços de persistência não configurados. Não é possível carregar dados.");
            return;
        }

        listaItensEstoque.clear();

        try {
            List<Vendavel> vendaveis = vendavelService.buscarTodosVendaveis();
            for (Vendavel vendavel : vendaveis) {
                listaItensEstoque.add(new EstoqueItem(vendavel));
            }
            System.out.println("Vendáveis carregados: " + vendaveis.size());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Vendáveis", "Não foi possível carregar dados de vendáveis: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            List<Insumo> insumos = insumoService.buscarTodosInsumos();
            for (Insumo insumo : insumos) {
                listaItensEstoque.add(new EstoqueItem(insumo));
            }
            System.out.println("Insumos carregados: " + insumos.size());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Carregar Insumos", "Não foi possível carregar dados de insumos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarEventos() {
        btnAdicionar.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ibdev/view/tela-estoque-gerente-adicionarProduto.fxml"));
                Parent root = loader.load();

                TelaAdicionarEstoque adicionarController = loader.getController();
                adicionarController.setEntityManager(entityManager);

                adicionarController.setOnProdutoAdded(novoProduto -> {
                    listaItensEstoque.add(new EstoqueItem(novoProduto));
                });

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Adicionar Item ao Estoque");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(btnAdicionar.getScene().getWindow());
                stage.setResizable(false);
                stage.showAndWait();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erro ao Abrir Tela", "Não foi possível carregar a tela de adição de estoque: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erro Inesperado", "Ocorreu um erro ao preparar a tela de adição: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void handleUpdateItem(Produto produto) {
        if (produto instanceof Vendavel) {
            showAlert(Alert.AlertType.INFORMATION, "Atualizar Vendável", "Editar Vendável: " + produto.getNome() + " (ID: " + produto.getId() + ")");
        } else if (produto instanceof Insumo) {
            showAlert(Alert.AlertType.INFORMATION, "Atualizar Insumo", "Editar Insumo: " + produto.getNome() + " (ID: " + produto.getId() + ")");
        }
    }

    private void handleDeleteItem(Produto produto) {
        if (produto instanceof Vendavel) {
            showAlert(Alert.AlertType.INFORMATION, "Excluir Vendável", "Excluir Vendável: " + produto.getNome() + " (ID: " + produto.getId() + ")");
        } else if (produto instanceof Insumo) {
            showAlert(Alert.AlertType.INFORMATION, "Excluir Insumo", "Excluir Insumo: " + produto.getNome() + " (ID: " + produto.getId() + ")");
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
}