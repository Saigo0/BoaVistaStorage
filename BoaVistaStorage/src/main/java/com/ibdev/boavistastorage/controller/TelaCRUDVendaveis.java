package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.service.VendavelService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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
    private ObservableList<Vendavel> listaVendaveis;

    private Vendavel vendavelSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabela();
        carregarTabela();

        tabelaVendaveis.setOnMouseClicked(this::selecionarVendavel);
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));
        colPrecoCusto.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecoCusto()));
        colPrecoVenda.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecoVenda()));
    }

    private void carregarTabela() {
        listaVendaveis = FXCollections.observableArrayList(vendavelService.buscarTodosVendaveis());
        tabelaVendaveis.setItems(listaVendaveis);
    }

    @FXML
    private void criarVendavel() {
        String nome = txtNome.getText();
        String fornecedor = txtFornecedor.getText();
        double precoCusto = Double.parseDouble(txtPrecoCusto.getText());
        double precoVenda = Double.parseDouble(txtPrecoVenda.getText());

        Vendavel novoVendavel = new Vendavel(nome, precoCusto, precoVenda);
        vendavelService.salvarVendavel(novoVendavel.getNome(), novoVendavel.getPrecoCusto(), novoVendavel.getPrecoVenda());
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
            carregarTabela();
        }
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
        txtFornecedor.clear();
        txtPrecoCusto.clear();
        txtPrecoVenda.clear();
        vendavelSelecionado = null;
    }
}
