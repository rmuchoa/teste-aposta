package com.aposta.pagamentos.business.relatorio.entity;

import com.aposta.pagamentos.business.pagador.entity.Pagador;

import java.math.BigDecimal;

public class RelatorioPagador {

    protected static final BigDecimal LIMITE_GERAL_POR_CONTA = BigDecimal.valueOf(3000.00);
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

    public void acrescentarValor(BigDecimal valor) {
        totalPago = totalPago.add(valor);
    }

    public boolean extrapolouLimiteGeralPorConta(BigDecimal valor) {
        return totalPago.add(valor)
                .compareTo(LIMITE_GERAL_POR_CONTA) > 0;
    }
}
