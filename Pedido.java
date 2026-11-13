package br.com.baozistore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Entidade Pedido.
 * Registra a compra de um produto por um cliente, em determinada quantidade.
 * Mantém os identificadores de cliente e produto, conforme o modelo simplificado
 * proposto no enunciado do trabalho.
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "o clienteId do pedido é obrigatório")
    private Long clienteId;

    @NotNull(message = "o produtoId do pedido é obrigatório")
    private Long produtoId;

    @NotNull(message = "a quantidade do pedido é obrigatória")
    @Min(value = 1, message = "a quantidade deve ser no mínimo 1")
    private Integer quantidade;

    public Pedido() {
    }

    public Pedido(Long clienteId, Long produtoId, Integer quantidade) {
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
