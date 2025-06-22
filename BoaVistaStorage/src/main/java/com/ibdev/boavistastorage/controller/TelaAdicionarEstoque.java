package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Insumo;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.entity.StatusEstoque;
// import com.ibdev.boavistastorage.entity.UnidadeDeMedida; // REMOVER ESTA IMPORTAÇÃO
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.repository.InsumoRepository;
import com.ibdev.boavistastorage.repository.VendavelRepository;
import com.ibdev.boavistastorage.service.InsumoService;
import com.ibdev.boavistastorage.service.VendavelService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField; // Já existe
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.RollbackException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class TelaAdicionarEstoque implements Initializable {

    @FXML private ComboBox<String> cmbTipoItem;
    @FXML private TextField txtNome;
    @FXML private TextField txtPrecoCusto;
    @FXML private TextField txtQuantidade;
    @FXML private Label lblPrecoVenda;
    @FXML private TextField txtPrecoVenda;
    @FXML private Label lblUnidadeMedida;
    @FXML private TextField txtUnidadeMedida; // MUDANÇA AQUI: de ComboBox para TextField
    @FXML private Button btnAdicionar;
    @FXML private Button btnCancelar;

    private EntityManager entityManager;
    private InsumoService insumoService;
    private VendavelService vendavelService;

    private Consumer<Produto> onProdutoAdded;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTipoItem.setItems(FXCollections.observableArrayList("Vendável", "Insumo"));
        cmbTipoItem.valueProperty().addListener((obs, oldVal, newVal) -> configurarCamposPorTipo(newVal));
        btnAdicionar.setOnAction(event -> handleAdicionar());
        btnCancelar.setOnAction(event -> closeStage());

        configurarCamposPorTipo(null);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.insumoService = new InsumoService(new InsumoRepository(entityManager));
        this.vendavelService = new VendavelService(new VendavelRepository(entityManager));
    }

    public void setOnProdutoAdded(Consumer<Produto> onProdutoAdded) {
        this.onProdutoAdded = onProdutoAdded;
    }

    private void configurarCamposPorTipo(String tipoSelecionado) {
        lblPrecoVenda.setVisible(false);
        txtPrecoVenda.setVisible(false);
        lblPrecoVenda.setManaged(false);
        txtPrecoVenda.setManaged(false);

        lblUnidadeMedida.setVisible(false);
        txtUnidadeMedida.setVisible(false);
        lblUnidadeMedida.setManaged(false);
        txtUnidadeMedida.setManaged(false);

        if ("Vendável".equals(tipoSelecionado)) {
            lblPrecoVenda.setVisible(true);
            txtPrecoVenda.setVisible(true);
            lblPrecoVenda.setManaged(true);
            txtPrecoVenda.setManaged(true);
            GridPane.setRowIndex(lblPrecoVenda, 4);
            GridPane.setRowIndex(txtPrecoVenda, 4);
        } else if ("Insumo".equals(tipoSelecionado)) {
            lblUnidadeMedida.setVisible(true);
            txtUnidadeMedida.setVisible(true);
            lblUnidadeMedida.setManaged(true);
            txtUnidadeMedida.setManaged(true);
            GridPane.setRowIndex(lblUnidadeMedida, 4);
            GridPane.setRowIndex(txtUnidadeMedida, 4);
        }
    }

    private void handleAdicionar() {
        if (!validarCampos()) {
            return;
        }

        String tipo = cmbTipoItem.getValue();
        String nome = txtNome.getText().trim();
        double precoCusto = Double.parseDouble(txtPrecoCusto.getText().replace(",", "."));
        double quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", "."));

        Produto novoProduto = null;

        try {
            entityManager.getTransaction().begin();

            if ("Vendável".equals(tipo)) {
                double precoVenda = Double.parseDouble(txtPrecoVenda.getText().replace(",", "."));
                Vendavel vendavel = new Vendavel();
                vendavel.setNome(nome);
                vendavel.setPrecoCusto(precoCusto);
                vendavel.setQuantEstoque(quantidade);
                vendavel.setPrecoVenda(precoVenda);
                vendavel.setStatusEstoque(calcularStatusEstoque(quantidade));
                vendavelService.salvarVendavel(vendavel.getNome(),
                                                vendavel.getPrecoCusto(),
                                                vendavel.getPrecoVenda(),
                                                vendavel.getQuantEstoque());
                novoProduto = vendavel;
            } else if ("Insumo".equals(tipo)) {
                String unidadeMedida = txtUnidadeMedida.getText().trim();
                Insumo insumo = new Insumo();
                insumo.setNome(nome);
                insumo.setPrecoCusto(precoCusto);
                insumo.setQuantidadeEstoque(quantidade);
                insumo.setUnidadeDeMedida(unidadeMedida);
                insumo.setStatusEstoque(calcularStatusEstoque(quantidade));
                insumoService.salvarInsumo(insumo.getNome(),
                                            insumo.getPrecoCusto(),
                                            insumo.getUnidadeDeMedida(),
                                            insumo.getQuantidadeEstoque());
                novoProduto = insumo;
            }

            entityManager.getTransaction().commit();
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Item adicionado ao estoque!");

            if (onProdutoAdded != null && novoProduto != null) {
                onProdutoAdded.accept(novoProduto);
            }

            closeStage();

        } catch (NumberFormatException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Preço ou quantidade inválidos. Use números e ponto/vírgula.");
        } catch (RollbackException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            showAlert(Alert.AlertType.ERROR, "Erro de Persistência", "Erro ao salvar no banco de dados. Verifique os dados. " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            showAlert(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        String tipo = cmbTipoItem.getValue();
        if (tipo == null || tipo.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validação", "Selecione o tipo de item (Vendável/Insumo).");
            return false;
        }
        if (txtNome.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validação", "O nome do item não pode ser vazio.");
            return false;
        }
        try {
            double precoCusto = Double.parseDouble(txtPrecoCusto.getText().replace(",", "."));
            if (precoCusto < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validação", "Preço de Custo inválido.");
            return false;
        }
        try {
            double quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", "."));
            if (quantidade < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validação", "Quantidade inválida.");
            return false;
        }

        if ("Vendável".equals(tipo)) {
            try {
                double precoVenda = Double.parseDouble(txtPrecoVenda.getText().replace(",", "."));
                if (precoVenda < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Validação", "Preço de Venda inválido.");
                return false;
            }
        } else if ("Insumo".equals(tipo)) {
            String unidadeMedida = txtUnidadeMedida.getText().trim();
            if (unidadeMedida.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validação", "Unidade de Medida não pode ser vazia.");
                return false;
            }
        }
        return true;
    }

    private StatusEstoque calcularStatusEstoque(double quantidade) {
        if (quantidade == 0) {
            return StatusEstoque.ZERADO;
        } else if (quantidade < 10) {
            return StatusEstoque.RAZOAVEL;
        } else {
            return StatusEstoque.BOM;
        }
    }

    private void closeStage() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}