package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.*;
import com.ibdev.boavistastorage.repository.CardapioRepository;
import com.ibdev.boavistastorage.repository.ProduzidoRepository;
import com.ibdev.boavistastorage.repository.VendavelRepository;
import com.ibdev.boavistastorage.service.CardapioService;
import com.ibdev.boavistastorage.main.SceneManager;

import com.ibdev.boavistastorage.service.ProduzidoService;
import com.ibdev.boavistastorage.service.VendavelService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaAdicionarProdutoCardapio implements Initializable {
    private EntityManager entityManager;

    @FXML
    private Button btnAdicionarProduto;

    @FXML
    private Button btnVoltar;

    @FXML
    private CheckBox checkBoxIsProduzido;

    @FXML
    private TextArea descricaoTextArea;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField precoTextField;

    private ProduzidoService produzidoService;
    private VendavelService vendavelService;

    private CardapioService cardapioService;
    private Cardapio cardapioAtual;
    private TelaCardapio telaCardapioController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando TelaAdicionarProdutoCardapio");
        Platform.runLater(this::configurarEventos);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.cardapioService = new CardapioService(new CardapioRepository(entityManager));
        this.produzidoService = new ProduzidoService(new ProduzidoRepository(entityManager));
        this.vendavelService = new VendavelService(new VendavelRepository(entityManager));
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapioAtual = cardapio;
        if (cardapioAtual != null) {
            System.out.println("Cardápio atual: " + cardapioAtual.getIdCardapio());
        } else {
            System.out.println("Nenhum cardápio foi definido.");
        }
    }

    public void setTelaCardapioController(TelaCardapio controller) {
        this.telaCardapioController = controller;
    }

    public void configurarEventos() {
        descricaoTextArea.editableProperty().bind(checkBoxIsProduzido.selectedProperty());

        checkBoxIsProduzido.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                descricaoTextArea.clear();
            }
        });

        btnVoltar.setOnAction(event -> {
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();
        });

        btnAdicionarProduto.setOnAction(event -> {
            String txtId = idTextField.getText().trim();
            String precoStr = precoTextField.getText().trim();
            String descricao = descricaoTextArea.getText().trim();

            if (txtId.isEmpty() || precoStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Por favor, preencha todos os campos.");
                return;
            }

            double preco;
            try {
                preco = Double.parseDouble(precoStr.replace(",", "."));
                if (preco <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Erro de Validação", "O preço deve ser um valor positivo.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Preço inválido. Por favor, insira um número válido.");
                return;
            }

            if (cardapioAtual == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Cardápio não foi carregado corretamente.");
                return;
            }

            try {
                try {
                    if (checkBoxIsProduzido.isSelected()) {
                        Produzido novoProduzido = produzidoService.buscarProduzidoPorId(Long.parseLong(txtId));

                        if (novoProduzido == null) {
                            showAlert(Alert.AlertType.ERROR, "Erro ao Buscar Produto", "Produzido não encontrado com o ID: " + txtId);
                            return;
                        }

                        if (descricao.trim().isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Descrição não pode estar vazia para produtos produzidos.");
                            return;
                        }

                        novoProduzido.setCardapio(cardapioAtual);
                        novoProduzido.setPrecoVenda(preco);
                        novoProduzido.setDescricao(descricao);
                        produzidoService.atualizarProduzido(Long.parseLong(txtId), novoProduzido);
                        cardapioAtual.addProdutosDisponiveis(novoProduzido);
                        entityManager.merge(cardapioAtual);
                        showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Produto adicionado com sucesso!");
                    } else {
                        Vendavel novoVendavel = vendavelService.buscarVendavelPorId(Long.parseLong(txtId));

                        if (novoVendavel == null) {
                            showAlert(Alert.AlertType.ERROR, "Erro ao Buscar Produto", "Vendável não encontrado com o ID: " + txtId);
                            return;
                        }

                        if (novoVendavel.getStatusEstoque() == null) {
                            if (novoVendavel.getQuantEstoque() <= 0) {
                                novoVendavel.setStatusEstoque(StatusEstoque.ZERADO);
                            } else if (novoVendavel.getQuantEstoque() < 20) {
                                novoVendavel.setStatusEstoque(StatusEstoque.RAZOAVEL);
                            } else {
                                novoVendavel.setStatusEstoque(StatusEstoque.BOM);

                            }
                        }

                        if (novoVendavel.getStatusEstoque().equals(StatusEstoque.ZERADO)) {
                            showAlert(Alert.AlertType.ERROR, "Erro de Estoque", "Produto está com estoque zerado. Não é possível adicioná-lo ao cardápio.");
                            idTextField.clear();
                            precoTextField.clear();
                            return;
                        }
                        novoVendavel.setCardapio(cardapioAtual);
                        novoVendavel.setPrecoVenda(preco);
                        vendavelService.atualizarVendavel(Long.parseLong(txtId), novoVendavel);
                        cardapioAtual.addProdutosDisponiveis(novoVendavel);
                        entityManager.merge(cardapioAtual);
                        showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Produto adicionado com sucesso!");
                    }
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erro de Validação", "ID inválido. Por favor, insira um número válido.");
                    return;
                } catch (RuntimeException e) {
                    showAlert(Alert.AlertType.ERROR, "Erro ao Buscar Produto", "Erro ao buscar produto");
                    return;
                }

            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                showAlert(Alert.AlertType.ERROR, "Erro ao Adicionar Produto", "Erro ao adicionar produto ao cardápio");
                e.printStackTrace();
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}