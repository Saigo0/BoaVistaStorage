package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Insumo;
import com.ibdev.boavistastorage.main.SceneManager;
import com.ibdev.boavistastorage.repository.InsumoRepository;
import com.ibdev.boavistastorage.service.InsumoService;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaCRUDInsumo implements Initializable {

    @FXML
    private Label btnMenuCardapio;

    @FXML
    public Label btnLogout;

    @FXML
    public Label btnGerentes;

    @FXML
    public Label btnAtendentes;

    @FXML
    public Label btnEstoque;

    @FXML
    public Label btnVendaveis;

    @FXML
    public Label btnInsumos;

    @FXML
    public Label btnInicio;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnCriar;

    @FXML
    private Button btnExcluir;



    @FXML
    private TableColumn<Insumo, String> colNome;

    @FXML
    private TableColumn<Insumo, Double> colPrecoCusto;

    @FXML
    private TableColumn<Insumo, String> colUnidadeDeMedida;

    @FXML
    private TableColumn<Insumo, Double> colQuantEstoque;

    @FXML
    private TableView<Insumo> tabelaInsumos;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPrecoCusto;

    @FXML
    private TextField txtUnidadeDeMedida;

    @FXML
    private TextField txtQuantEstoque;

    private InsumoService insumoService;
    private List<Insumo> insumos;
    private ObservableList<Insumo> listaInsumos;
    private InsumoRepository insumoRepository;
    private Insumo insumoSelecionado;
    private EntityManager entityManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabela();
        configurarEventos();
        tabelaInsumos.setOnMouseClicked(this::selecionarVendavel);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.insumoService = new InsumoService(new InsumoRepository(entityManager));
        carregarTabela();
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colPrecoCusto.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrecoCusto()));
        colUnidadeDeMedida.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnidadeDeMedida()));
        colQuantEstoque.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantidadeEstoque()));
    }

    private void carregarTabela() {
        listaInsumos = FXCollections.observableArrayList(insumoService.buscarTodosInsumos());
        tabelaInsumos.setItems(listaInsumos);
    }

    @FXML
    private void criarInsumo() {
        String nome = txtNome.getText();
        double precoCusto = Double.parseDouble(txtPrecoCusto.getText());
        String unidadeDeMedida = txtUnidadeDeMedida.getText();
        double quantEstoque = Double.parseDouble(txtQuantEstoque.getText());

        Insumo novoInsumo = new Insumo(nome, precoCusto, unidadeDeMedida, quantEstoque);
        insumoService.salvarInsumo(novoInsumo);
        limparCampos();
        carregarTabela();
    }

    @FXML
    private void atualizarInsumo() {
        if (insumoSelecionado != null) {
            insumoSelecionado.setNome(txtNome.getText());
            insumoSelecionado.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText()));
            insumoSelecionado.setUnidadeDeMedida(txtUnidadeDeMedida.getText());
            insumoSelecionado.setQuantidadeEstoque(Double.parseDouble(txtQuantEstoque.getText()));
            insumoService.atualizarInsumo(insumoSelecionado.getId(), insumoSelecionado);
            limparCampos();
            tabelaInsumos.refresh();

        }
    }

    public void configurarEventos(){
        btnMenuCardapio.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-cardapio.fxml", "Cardápio", entityManager
            );
        });

        btnAtualizar.setOnMouseClicked(event -> {
            atualizarInsumo();
        });

        btnCriar.setOnMouseClicked(event -> {
            criarInsumo();
        });

        btnExcluir.setOnMouseClicked(event -> {
            excluirInsumo();
        });

        btnVendaveis.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-crud-vendaveis.fxml", "Vendaveis", entityManager
            );
        });

        btnEstoque.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-estoque-gerente.fxml", "Estoque", entityManager
            );
        });

        btnInicio.setOnMouseClicked(event -> {
            SceneManager.mudarCenaMaximizada(
                    "/com/ibdev/view/tela-principal-gerente.fxml", "Inicio", entityManager
            );
        });

    }

    @FXML
    private void excluirInsumo() {
        if (insumoSelecionado != null) {
            insumoService.deletarInsumo(insumoSelecionado.getId());
            limparCampos();
            carregarTabela();
        }
    }

    private void selecionarVendavel(javafx.scene.input.MouseEvent event) {
        insumoSelecionado = tabelaInsumos.getSelectionModel().getSelectedItem();
        if (insumoSelecionado != null) {
            txtNome.setText(insumoSelecionado.getNome());
            txtPrecoCusto.setText(String.valueOf(insumoSelecionado.getPrecoCusto()));
            txtUnidadeDeMedida.setText(insumoSelecionado.getUnidadeDeMedida());
            txtQuantEstoque.setText(String.valueOf(insumoSelecionado.getQuantidadeEstoque()));
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtPrecoCusto.clear();
        txtUnidadeDeMedida.clear();
        txtQuantEstoque.clear();
        insumoSelecionado = null;
    }
}
