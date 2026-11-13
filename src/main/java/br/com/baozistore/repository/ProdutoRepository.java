package br.com.baozistore.repository;

import br.com.baozistore.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de acesso aos dados de Produto (Spring Data JPA).
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
