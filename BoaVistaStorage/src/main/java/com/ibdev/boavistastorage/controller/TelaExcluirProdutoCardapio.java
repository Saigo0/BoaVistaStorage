package com.ibdev.boavistastorage.controller;

import com.ibdev.boavistastorage.entity.Cardapio;
import com.ibdev.boavistastorage.entity.Produto;
import com.ibdev.boavistastorage.entity.Produzido;
import com.ibdev.boavistastorage.entity.Vendavel;
import com.ibdev.boavistastorage.repository.CardapioRepository;
import com.ibdev.boavistastorage.repository.ProduzidoRepository;
import com.ibdev.boavistastorage.repository.VendavelRepository;
import com.ibdev.boavistastorage.service.CardapioService;
import com.ibdev.boavistastorage.service.ProduzidoService;
import com.ibdev.boavistastorage.service.VendavelService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TelaExcluirProdutoCardapio implements Initializable {
    private EntityManager entityManager;

    @FXML
    private Button btnExcluirProduto;

    @FXML
    private Button btnVoltar;

    @FXML
    private CheckBox checkBoxIsProduzido;

    @FXML
    private TextField idTextField;

    @FXML
    private Label nomeProdutoConfirmacaoLabel;

    private ProduzidoService produzidoService;
    private VendavelService vendavelService;
    private CardapioService cardapioService; // Para gerenciar o cardápio

    private Cardapio cardapioAtual;
    private TelaCardapio telaCardapioController; // Referência para atualizar a tela principal

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando TelaExcluirProdutoCardapio");
        Platform.runLater(this::configurarEventos);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.produzidoService = new ProduzidoService(new ProduzidoRepository(entityManager));
        this.vendavelService = new VendavelService(new VendavelRepository(entityManager));
        this.cardapioService = new CardapioService(new CardapioRepository(entityManager));
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapioAtual = cardapio;
        if (cardapioAtual != null) {
            System.out.println("Cardápio atual para exclusão: " + cardapioAtual.getIdCardapio());
        } else {
            System.out.println("Nenhum cardápio foi definido para exclusão.");
        }
    }

    public void setTelaCardapioController(TelaCardapio controller) {
        this.telaCardapioController = controller;
    }

    public void configurarEventos() {
        btnVoltar.setOnAction(event -> {
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.close();
        });

        idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.trim().isEmpty()) {
                buscarProdutoParaConfirmacao(newValue.trim());
            } else {
                nomeProdutoConfirmacaoLabel.setText("[Nenhum produto selecionado]");
            }
        });

        checkBoxIsProduzido.setOnAction(event -> {
            if (!idTextField.getText().trim().isEmpty()) {
                buscarProdutoParaConfirmacao(idTextField.getText().trim());
            }
        });

        btnExcluirProduto.setOnAction(event -> {
            String txtId = idTextField.getText().trim();

            if (txtId.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Por favor, preencha o ID do produto.");
                return;
            }

            Long produtoId;
            try {
                produtoId = Long.parseLong(txtId);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erro de Validação", "ID inválido. Por favor, insira um número válido.");
                return;
            }

            if (cardapioAtual == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Cardápio não foi carregado corretamente.");
                return;
            }

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmar Exclusão");
            confirmAlert.setHeaderText("Tem certeza que deseja excluir este item do cardápio?");
            confirmAlert.setContentText("Produto ID: " + produtoId + "\nNome: " + nomeProdutoConfirmacaoLabel.getText());

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                excluirProdutoDoCardapio(produtoId);
            }
        });
    }

    private void buscarProdutoParaConfirmacao(String idText) {
        try {
            Long produtoId = Long.parseLong(idText);
            Produto produtoEncontrado = null;

            if (checkBoxIsProduzido.isSelected()) {
                produzidoService.buscarProduzidoPorId(produtoId);
                Optional<Produzido> produzidoOpt = cardapioAtual.getProdutosDisponiveis().stream()
                        .filter(p -> p instanceof Produzido && p.getId().equals(produtoId))
                        .map(p -> (Produzido) p)
                        .findFirst();
                if(produzidoOpt.isPresent()) {
                    produtoEncontrado = produzidoOpt.get();
                }

            } else {
                vendavelService.buscarVendavelPorId(produtoId);
                Optional<Vendavel> vendavelOpt = cardapioAtual.getProdutosDisponiveis().stream()
                        .filter(p -> p instanceof Vendavel && p.getId().equals(produtoId))
                        .map(p -> (Vendavel) p)
                        .findFirst();
                if(vendavelOpt.isPresent()) {
                    produtoEncontrado = vendavelOpt.get();
                }
            }

            if (produtoEncontrado != null && cardapioAtual.getProdutosDisponiveis().contains(produtoEncontrado)) {
                nomeProdutoConfirmacaoLabel.setText(produtoEncontrado.getNome());
            } else {
                nomeProdutoConfirmacaoLabel.setText("[Produto não encontrado no cardápio ou ID incorreto]");
            }
        } catch (NumberFormatException e) {
            nomeProdutoConfirmacaoLabel.setText("[ID inválido]");
        } catch (NoResultException e) {
            nomeProdutoConfirmacaoLabel.setText("[Produto não encontrado]");
        } catch (Exception e) {
            nomeProdutoConfirmacaoLabel.setText("[Erro ao buscar produto]");
            e.printStackTrace();
        }
    }

    private void excluirProdutoDoCardapio(Long produtoId) {
        entityManager.getTransaction().begin();
        try {
            Produto produtoParaRemover = null;

            if (checkBoxIsProduzido.isSelected()) {
                Produzido produzido = produzidoService.buscarProduzidoPorId(produtoId);
                if (produzido != null && cardapioAtual.getProdutosDisponiveis().contains(produzido)) {
                    produtoParaRemover = produzido;
                }
            } else {
                Vendavel vendavel = vendavelService.buscarVendavelPorId(produtoId);
                if (vendavel != null && cardapioAtual.getProdutosDisponiveis().contains(vendavel)) {
                    produtoParaRemover = vendavel;
                }
            }

            if (produtoParaRemover == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Produto com ID " + produtoId + " não encontrado ou não faz parte do cardápio atual.");
                entityManager.getTransaction().rollback();
                return;
            }

            cardapioAtual.removeProdutosDisponiveis(produtoParaRemover);

            produtoParaRemover.setCardapio(null);


            entityManager.merge(cardapioAtual);
            entityManager.merge(produtoParaRemover);

            entityManager.getTransaction().commit();

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Produto removido do cardápio com sucesso!");

            if (telaCardapioController != null) {
                telaCardapioController.carregarProdutosNoCardapio();
            }

            Stage stage = (Stage) btnExcluirProduto.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            showAlert(Alert.AlertType.ERROR, "Erro ao Excluir Produto", "Erro: " + e.getMessage());
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
}