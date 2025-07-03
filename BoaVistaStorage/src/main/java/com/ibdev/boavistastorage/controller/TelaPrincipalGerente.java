package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.EstoqueItem;
import com.ibdev.boavistastorage.entity.Insumo;
import com.ibdev.boavistastorage.entity.StatusEstoque;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.main.SceneManager;
import com.ibdev.boavistastorage.repository.InsumoRepository;
import com.ibdev.boavistastorage.repository.VendavelRepository;
import com.ibdev.boavistastorage.service.InsumoService;
import com.ibdev.boavistastorage.service.VendavelService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import jakarta.persistence.EntityManager;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaPrincipalGerente implements Initializable {

    @FXML
    public Label btnVendaveis;

    @FXML
    public Label btnInsumos;

    @FXML
    private Button btnIrParaEstoque;

    @FXML
    private Label btnMenuAtendentes;

    @FXML
    private Label btnMenuEstoque;

    @FXML
    private Label btnMenuGerentes;

    @FXML
    private Label btnMenuInicio;

    @FXML
    private Label btnSair;




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
    private Label produtoEstoqueBom;

    @FXML
    private Label produtosRazoaveis;

    @FXML
    private Label produtosZerados;

    @FXML
    private TableView<EstoqueItem> tabelaEstoque;

    private ObservableList<EstoqueItem> listaItensEstoque = FXCollections.observableArrayList();

    private InsumoService insumoService;
    private VendavelService vendavelService;
    private EntityManager entityManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando TelaPrincipalGerente");
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
        verificaStatusEstoque();
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
    }

    private void configurarEventos() {

        btnSair.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-login.fxml", "Login - Boa Vista Storage", entityManager
            );
        });

        btnIrParaEstoque.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-estoque-gerente.fxml", "Estoque - Boa Vista Storage", entityManager
            );
        });

        btnMenuEstoque.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-estoque-gerente.fxml", "Estoque - Boa Vista Storage", entityManager
            );
        });


        btnInsumos.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-crud-insumos.fxml", "Insumos - Boa Vista Storage", entityManager
            );
        });

        btnVendaveis.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-crud-vendaveis.fxml", "Vendaveis - Boa Vista Storage", entityManager
            );
        });
    }

    public void verificaStatusEstoque() {
        int armazenaEstoqueBom = 0;
        int armazenaEstoqueRazoavel = 0;
        int armazenaEstoqueZerado = 0;

        for (Insumo insumo : insumoService.buscarTodosInsumos()) {
            if (insumo.getStatusEstoque() == StatusEstoque.BOM) {
                armazenaEstoqueBom++;
            } else if (insumo.getStatusEstoque() == StatusEstoque.RAZOAVEL) {
                armazenaEstoqueRazoavel++;
            } else if (insumo.getStatusEstoque() == StatusEstoque.ZERADO) {
                armazenaEstoqueZerado++;
            }
        }
        for (Vendavel vendavel : vendavelService.buscarTodosVendaveis()) {
            if (vendavel.getStatusEstoque() == StatusEstoque.BOM) {
                armazenaEstoqueBom++;
            } else if (vendavel.getStatusEstoque() == StatusEstoque.RAZOAVEL) {
                armazenaEstoqueRazoavel++;
            } else if (vendavel.getStatusEstoque() == StatusEstoque.ZERADO) {
                armazenaEstoqueZerado++;
            }
        }

        produtoEstoqueBom.setText(String.valueOf(armazenaEstoqueBom));
        produtosRazoaveis.setText(String.valueOf(armazenaEstoqueRazoavel));
        produtosZerados.setText(String.valueOf(armazenaEstoqueZerado));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
