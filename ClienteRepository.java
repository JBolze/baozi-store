package br.com.baozistore.repository;

import br.com.baozistore.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de acesso aos dados de Cliente (Spring Data JPA).
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
