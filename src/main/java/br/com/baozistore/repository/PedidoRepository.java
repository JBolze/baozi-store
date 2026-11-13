package br.com.baozistore.repository;

import br.com.baozistore.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de acesso aos dados de Pedido (Spring Data JPA).
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
