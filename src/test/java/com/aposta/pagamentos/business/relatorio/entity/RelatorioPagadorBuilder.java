package com.aposta.pagamentos.business.relatorio.entity;

import com.aposta.pagamentos.business.pagador.entity.Pagador;
import com.aposta.pagamentos.business.pagador.entity.PagadorPessoaFisicaBuilder;

import java.math.BigDecimal;

public class RelatorioPagadorBuilder {

    private Pagador pagador;
    private BigDecimal totalPago;

    private RelatorioPagadorBuilder() {}

    public RelatorioPagadorBuilder comPagador(Pagador pagador) {
        this.pagador = pagador;
        return this;
    }

    public RelatorioPagadorBuilder comAlgumPagador() {
        return comPagador(PagadorPessoaFisicaBuilder.builder()
                .comAlgumCpf()
                .comDesoitoAnos()
                .build());
    }

    public RelatorioPagadorBuilder comPagadorComCpf(String cpf) {
        return comPagador(PagadorPessoaFisicaBuilder.builder()
                .comCpf(cpf)
                .comDesoitoAnos()
                .build());
    }

    public RelatorioPagadorBuilder comTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
        return this;
    }

    public RelatorioPagadorBuilder comTotalPagoNoLimiteGeralPorConta() {
        return comTotalPago(RelatorioPagador.LIMITE_GERAL_POR_CONTA);
    }

    public static RelatorioPagador buildRelatorioLimiteAtingidoPagadorComCpf(String cpf) {
        return builder()
                .comPagadorComCpf(cpf)
                .comTotalPagoNoLimiteGeralPorConta()
                .build();
    }

    public static RelatorioPagador buildRelatorioComPagadorComCpf(String cpf) {
        return builder()
                .comPagadorComCpf(cpf)
                .build();
    }

    public static RelatorioPagador buildRelatorioComTotalJaPago(BigDecimal totalJaPago) {
        return builder()
                .comAlgumPagador()
                .comTotalPago(totalJaPago)
                .build();
    }

    public static RelatorioPagadorBuilder builder() {
        return new RelatorioPagadorBuilder();
    }

    public RelatorioPagador build() {
        RelatorioPagador relatorio = new RelatorioPagador(pagador);
        if (totalPago != null) relatorio.acrescentarValor(totalPago);
        return relatorio;
    }

}
