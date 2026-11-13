package br.com.baozistore.exception;

/**
 * Lançada quando um recurso solicitado não existe na base de dados.
 * O tratamento devolve HTTP 404 (Not Found).
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
