package com.aposta.exception;

public class TransacaoNaoPermitidaException extends RuntimeException {

    public TransacaoNaoPermitidaException(String motivo) {
        super(String.format("Esta transação não é permitida: %s!", motivo));
    }

}
