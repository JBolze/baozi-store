package br.com.baozistore.exception;

/**
 * Lançada quando a requisição viola uma regra de negócio da loja,
 * por exemplo criar um pedido para um cliente ou produto inexistente.
 * O tratamento devolve HTTP 422 (Unprocessable Entity).
 */
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
