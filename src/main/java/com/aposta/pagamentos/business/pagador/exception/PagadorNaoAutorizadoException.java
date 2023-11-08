package com.aposta.pagamentos.business.pagador.exception;

public class PagadorNaoAutorizadoException extends RuntimeException {

    private String motivo;

    public PagadorNaoAutorizadoException(String motivo) {
        super(String.format("Pagador não autorizado: %s!", motivo));
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }
}
