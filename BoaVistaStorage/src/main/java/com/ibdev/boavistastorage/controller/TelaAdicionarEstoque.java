package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Insumo;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.entity.StatusEstoque;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class TelaAdicionarEstoque implements Initializable {

    @FXML
    private ComboBox<String> cmbTipoItem;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtPrecoCusto;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private Label lblPrecoVenda;
    @FXML
    private TextField txtPrecoVenda;
    @FXML
    private Label lblUnidadeMedida;
    @FXML
    private TextField txtUnidadeMedida;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnCancelar;

    private EntityManager entityManager;
    private InsumoService insumoService;
    private VendavelService vendavelService;

    private Produto produtoParaEdicao;
    private Consumer<Produto> onProdutoActionCompleted;

    private final NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTipoItem.setItems(FXCollections.observableArrayList("Vendável", "Insumo"));
        cmbTipoItem.valueProperty().addListener((obs, oldVal, newVal) -> configurarCamposPorTipo(newVal));
        btnAdicionar.setOnAction(event -> handleSalvar());
        btnCancelar.setOnAction(event -> closeStage());

        configurarCamposPorTipo(null);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.insumoService = new InsumoService(new InsumoRepository(entityManager));
        this.vendavelService = new VendavelService(new VendavelRepository(entityManager));
    }

    public void setProdutoParaEdicao(Produto produto) {
        this.produtoParaEdicao = produto;
        if (produtoParaEdicao != null) {
            cmbTipoItem.setDisable(true);
            btnAdicionar.setText("Salvar Alterações");

            txtNome.setText(produtoParaEdicao.getNome());
            txtPrecoCusto.setText(numberFormat.format(produtoParaEdicao.getPrecoCusto()));

            if (produtoParaEdicao instanceof Vendavel) {
                Vendavel vendavel = (Vendavel) produtoParaEdicao;
                cmbTipoItem.setValue("Vendável");
                txtQuantidade.setText(numberFormat.format(vendavel.getQuantEstoque()));
                txtPrecoVenda.setText(numberFormat.format(vendavel.getPrecoVenda()));
            } else if (produtoParaEdicao instanceof Insumo) {
                Insumo insumo = (Insumo) produtoParaEdicao;
                cmbTipoItem.setValue("Insumo");
                txtQuantidade.setText(numberFormat.format(insumo.getQuantidadeEstoque()));
                txtUnidadeMedida.setText(insumo.getUnidadeDeMedida());
            }
            configurarCamposPorTipo(cmbTipoItem.getValue());
        }
    }

    public void setOnProdutoActionCompleted(Consumer<Produto> onProdutoActionCompleted) {
        this.onProdutoActionCompleted = onProdutoActionCompleted;
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
        } else if ("Insumo".equals(tipoSelecionado)) {
            lblUnidadeMedida.setVisible(true);
            txtUnidadeMedida.setVisible(true);
            lblUnidadeMedida.setManaged(true);
            txtUnidadeMedida.setManaged(true);
        }
    }

    private void handleSalvar() {
        if (!validarCampos()) {
            return;
        }

        String tipo = cmbTipoItem.getValue();
        String nome = txtNome.getText().trim();
        double precoCusto = parseNumber(txtPrecoCusto.getText());
        double quantidade = parseNumber(txtQuantidade.getText());

        Produto produtoResultante = null;

        try {
            if (produtoParaEdicao == null) {
                if ("Vendável".equals(tipo)) {
                    double precoVenda = parseNumber(txtPrecoVenda.getText());
                    Vendavel vendavel = new Vendavel();
                    vendavel.setNome(nome);
                    vendavel.setPrecoCusto(precoCusto);
                    vendavel.setQuantEstoque(quantidade);
                    vendavel.setPrecoVenda(precoVenda);
                    vendavel.setStatusEstoque(calcularStatusEstoque(quantidade));
                    produtoResultante = vendavelService.salvarVendavel(vendavel);
                } else if ("Insumo".equals(tipo)) {
                    String unidadeMedida = txtUnidadeMedida.getText().trim();
                    Insumo insumo = new Insumo();
                    insumo.setNome(nome);
                    insumo.setPrecoCusto(precoCusto);
                    insumo.setQuantidadeEstoque(quantidade);
                    insumo.setUnidadeDeMedida(unidadeMedida);
                    // AQUI ESTÁ A CORREÇÃO: Usando calcularStatusEstoqueInsumo para novos Insumos
                    insumo.setStatusEstoque(calcularStatusEstoqueInsumo(quantidade, insumo));
                    produtoResultante = insumoService.salvarInsumo(insumo);
                }
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Item adicionado ao estoque!");

            } else {
                produtoParaEdicao.setNome(nome);
                produtoParaEdicao.setPrecoCusto(precoCusto);

                if (produtoParaEdicao instanceof Vendavel) {
                    Vendavel vendavel = (Vendavel) produtoParaEdicao;
                    vendavel.setQuantEstoque(quantidade);
                    vendavel.setPrecoVenda(parseNumber(txtPrecoVenda.getText()));
                    vendavel.setStatusEstoque(calcularStatusEstoque(quantidade));
                    produtoResultante = vendavelService.atualizarVendavel(vendavel.getId(), vendavel);
                } else if (produtoParaEdicao instanceof Insumo) {
                    Insumo insumo = (Insumo) produtoParaEdicao;
                    insumo.setQuantidadeEstoque(quantidade);
                    insumo.setUnidadeDeMedida(txtUnidadeMedida.getText().trim());
                    // Já estava correto para atualização de Insumo
                    insumo.setStatusEstoque(calcularStatusEstoqueInsumo(quantidade, insumo));
                    produtoResultante = insumoService.atualizarInsumo(insumo.getId(), insumo);
                }
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Item atualizado com sucesso!");
            }

            if (onProdutoActionCompleted != null && produtoResultante != null) {
                onProdutoActionCompleted.accept(produtoResultante);
            }

            closeStage();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Preço ou quantidade inválidos. Verifique os valores.");
            e.printStackTrace();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Persistência", "Erro ao salvar/atualizar no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private double parseNumber(String text) {
        try {
            return numberFormat.parse(text).doubleValue();
        } catch (java.text.ParseException e) {
            throw new NumberFormatException("Formato de número inválido: " + text);
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
            double precoCusto = parseNumber(txtPrecoCusto.getText());
            if (precoCusto < 0) {
                showAlert(Alert.AlertType.WARNING, "Validação", "Preço de Custo não pode ser negativo.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validação", "Preço de Custo inválido.");
            return false;
        }
        try {
            double quantidade = parseNumber(txtQuantidade.getText());
            if (quantidade < 0) {
                showAlert(Alert.AlertType.WARNING, "Validação", "Quantidade não pode ser negativa.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validação", "Quantidade inválida.");
            return false;
        }

        if ("Vendável".equals(tipo)) {
            try {
                double precoVenda = parseNumber(txtPrecoVenda.getText());
                if (precoVenda < 0) {
                    showAlert(Alert.AlertType.WARNING, "Validação", "Preço de Venda não pode ser negativo.");
                    return false;
                }
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

    private StatusEstoque calcularStatusEstoqueInsumo(double quantidade, Insumo insumo) {
        try {
            if (insumo.getUnidadeDeMedida().equalsIgnoreCase("kg")) {
                if (quantidade == 0) {
                    return StatusEstoque.ZERADO;
                }
                if (quantidade < 2) {
                    return StatusEstoque.RAZOAVEL;
                } else {
                    return StatusEstoque.BOM;
                }
            } else if (insumo.getUnidadeDeMedida().equalsIgnoreCase("L")) {
                if (quantidade == 0) {
                    return StatusEstoque.ZERADO;
                }
                if (quantidade < 5) {
                    return StatusEstoque.RAZOAVEL;
                } else {
                    return StatusEstoque.BOM;
                }
            }

            showAlert(Alert.AlertType.WARNING, "Unidade de Medida Inválida",
                    "Unidade de medida '" + insumo.getUnidadeDeMedida() + "' não reconhecida. Status de estoque padrão aplicado.");
            return StatusEstoque.RAZOAVEL; //
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Cálculo", "Erro ao calcular o status do estoque: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void closeStage() {
        Stage stage = (Stage) btnAdicionar.getScene().getWindow();
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