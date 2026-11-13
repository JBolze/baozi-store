package br.com.baozistore.controller;

import br.com.baozistore.exception.RecursoNaoEncontradoException;
import br.com.baozistore.exception.RegraDeNegocioException;
import br.com.baozistore.model.Pedido;
import br.com.baozistore.repository.ClienteRepository;
import br.com.baozistore.repository.PedidoRepository;
import br.com.baozistore.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST de Pedido.
 *
 * POST   /pedidos       — registrar pedido
 * GET    /pedidos       — listar todos os pedidos
 * GET    /pedidos/{id}  — consultar pedido por id
 * PUT    /pedidos/{id}  — atualizar pedido (opcional no enunciado)
 * DELETE /pedidos/{id}  — apagar pedido
 *
 * Regras de negócio: o cliente e o produto informados precisam existir.
 */
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository,
                            ClienteRepository clienteRepository,
                            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody Pedido pedido) {
        validarReferencias(pedido.getClienteId(), pedido.getProdutoId());
        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("pedido " + id + " não encontrado"));
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @Valid @RequestBody Pedido dados) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("pedido " + id + " não encontrado"));

        validarReferencias(dados.getClienteId(), dados.getProdutoId());

        pedido.setClienteId(dados.getClienteId());
        pedido.setProdutoId(dados.getProdutoId());
        pedido.setQuantidade(dados.getQuantidade());

        return ResponseEntity.ok(pedidoRepository.save(pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("pedido " + id + " não encontrado"));

        pedidoRepository.delete(pedido);
        return ResponseEntity.noContent().build();
    }

    /** Garante que o cliente e o produto do pedido existem antes de gravar. */
    private void validarReferencias(Long clienteId, Long produtoId) {
        if (clienteId == null || !clienteRepository.existsById(clienteId)) {
            throw new RegraDeNegocioException("cliente " + clienteId + " não cadastrado");
        }
        if (produtoId == null || !produtoRepository.existsById(produtoId)) {
            throw new RegraDeNegocioException("produto " + produtoId + " não cadastrado");
        }
    }
}
