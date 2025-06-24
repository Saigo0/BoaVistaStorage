package com.ibdev.boavistastorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn; // Adicione esta importação
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient; // Importe para campos não persistentes

@Entity
@Table(name = "itemPedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemPedido;

    @Column(nullable = false)
    private int quantidade;

    @OneToOne // Assumindo que um ItemPedido se refere a um único Produto
    @JoinColumn(name = "produto_id", nullable = false) // Garante que a coluna de associação não seja nula
    private Produto produto;

    @ManyToOne // Vários ItemPedido para um Pedido
    @JoinColumn(name = "pedido_id") // Coluna no banco de dados para a FK de Pedido
    private Pedido pedido;

    @Transient // Indica que este campo NÃO será persistido no banco de dados
    private Object userData; // Campo para armazenar dados da UI (como o HBox do mini-card)

    // Construtor padrão exigido pelo JPA
    public ItemPedido() {
    }

    // Construtor com parâmetros para facilitar a criação
    public ItemPedido(int quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
    }

    // --- Getters e Setters ---

    public Long getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(Long idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    // Getter e Setter para o campo transient userData
    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItemPedido=" + idItemPedido +
                ", quantidade=" + quantidade +
                ", produto=" + (produto != null ? produto.getNome() : "null") +
                '}';
    }
}