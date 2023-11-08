package com.aposta.pagamentos.business.transacao.exception;

public class TransacaoNaoPermitidaException extends RuntimeException {

    private String motivo;

    public TransacaoNaoPermitidaException(String motivo) {
        super(String.format("Esta transação não é permitida: %s!", motivo));
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }
}
