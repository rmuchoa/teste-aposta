package com.aposta.pagamentos.business.entity;

import java.math.BigDecimal;

public class RelatorioPagador {

    private Pagador pagador;
    private BigDecimal totalPago;

    public RelatorioPagador(Pagador pagador) {
        this.pagador = pagador;
        this.totalPago = BigDecimal.ZERO;
    }

    public Pagador getPagador() {
        return pagador;
    }

    public BigDecimal getTotalPago() {
        return totalPago;
    }

}
