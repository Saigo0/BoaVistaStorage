package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.main.SceneManager;
import com.ibdev.boavistastorage.repository.VendavelRepository;
import com.ibdev.boavistastorage.service.VendavelService;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaCRUDVendaveis implements Initializable {


    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnCriar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TableColumn<Vendavel, String> colFornecedor;

    @FXML
    private TableColumn<Vendavel, String> colNome;

    @FXML
    private TableColumn<Vendavel, Double> colPrecoCusto;

    @FXML
    private TableColumn<Vendavel, Double> colPrecoVenda;

    @FXML
    private TableView<Vendavel> tabelaVendaveis;

    @FXML
    private TextField txtFornecedor;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPrecoCusto;

    @FXML
    private TextField txtPrecoVenda;

    private VendavelService vendavelService;
    private List<Vendavel> vendaveis;
    private ObservableList<Vendavel> listaVendaveis;
    private VendavelRepository vendavelRepository;
    private Vendavel vendavelSelecionado;
    private EntityManager entityManager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabela();
        configurarEventos();
        tabelaVendaveis.setOnMouseClicked(this::selecionarVendavel);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.vendavelService = new VendavelService(new VendavelRepository(entityManager));
        carregarTabela();
    }



    private void configurarTabela() {
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colPrecoCusto.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrecoCusto()));
        colPrecoVenda.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrecoVenda()));
    }

    private void carregarTabela() {
        listaVendaveis = FXCollections.observableArrayList(vendavelService.buscarTodosVendaveis());
        tabelaVendaveis.setItems(listaVendaveis);
    }

    @FXML
    private void criarVendavel() {
        String nome = txtNome.getText();
        double precoCusto = Double.parseDouble(txtPrecoCusto.getText());
        double precoVenda = Double.parseDouble(txtPrecoVenda.getText());

        Vendavel novoVendavel = new Vendavel(nome, precoCusto, precoVenda);
        vendavelService.salvarVendavel(novoVendavel);
        limparCampos();
        carregarTabela();
    }

    @FXML
    private void atualizarVendavel() {
        if (vendavelSelecionado != null) {
            vendavelSelecionado.setNome(txtNome.getText());
            vendavelSelecionado.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText()));
            vendavelSelecionado.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText()));
            vendavelService.atualizarVendavel(vendavelSelecionado.getId(), vendavelSelecionado);
            limparCampos();
            tabelaVendaveis.refresh();
        }
    }

    public void configurarEventos(){
        btnAtualizar.setOnMouseClicked(event -> {
            atualizarVendavel();
        });

        btnCriar.setOnMouseClicked(event -> {
            criarVendavel();
        });

        btnExcluir.setOnMouseClicked(event -> {
            excluirVendavel();
        });
    }

    @FXML
    private void excluirVendavel() {
        if (vendavelSelecionado != null) {
            vendavelService.deletarVendavel(vendavelSelecionado.getId());
            limparCampos();
            carregarTabela();
        }
    }

    private void selecionarVendavel(MouseEvent event) {
        vendavelSelecionado = tabelaVendaveis.getSelectionModel().getSelectedItem();
        if (vendavelSelecionado != null) {
            txtNome.setText(vendavelSelecionado.getNome());
            txtPrecoCusto.setText(String.valueOf(vendavelSelecionado.getPrecoCusto()));
            txtPrecoVenda.setText(String.valueOf(vendavelSelecionado.getPrecoVenda()));
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtPrecoCusto.clear();
        txtPrecoVenda.clear();
        vendavelSelecionado = null;
    }



}
