package com.aposta.pagamentos.business.exception;

public class TransacaoNaoPagaException extends RuntimeException {

    public TransacaoNaoPagaException(String motivo) {
        super(String.format("Não foi possível pagar a transação: %s!", motivo));
    }
}
