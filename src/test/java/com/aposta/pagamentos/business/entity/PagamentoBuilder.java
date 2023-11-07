package com.aposta.pagamentos.business.entity;

import java.math.BigDecimal;

public class PagamentoBuilder {

    private Pagador pagador;
    private String qrCode;
    private BigDecimal valor;

    private PagamentoBuilder() {}

    public static PagamentoBuilder builder() {
        return new PagamentoBuilder();
    }

    public static TransacaoPagamento buildPagamentoPessoaJuridica() {
        return builder()
                .comPagadorPessoaJuridica()
                .build();
    }

    public static TransacaoPagamento buildPagamentoMenorDeIdade() {
        return builder()
                .comPagadorMenorDeIdade()
                .build();
    }

    public static TransacaoPagamento buildPagamentoComNenhumQRCode() {
        return builder()
                .comPagadorDeDesoitoAnosDeIdade()
                .comNenhumQrCode()
                .build();
    }

    public static TransacaoPagamento buildPagamentoComValorQuinhentosMais() {
        return builder()
                .comPagadorDeDesoitoAnosDeIdade()
                .comAlgumQrCode()
                .comMaisDeQuinhentosDeValor()
                .build();
    }

    public static TransacaoPagamento buildPagamentoValido() {
        return builder()
                .comPagadorDeDesoitoAnosDeIdade()
                .comAlgumQrCode()
                .comQuinhentosDeValor()
                .build();
    }

    public PagamentoBuilder comPagador(Pagador pagador) {
        this.pagador = pagador;
        return this;
    }

    public PagamentoBuilder comPagadorPessoaJuridica() {
        return comPagador(PagadorBuilder.builder()
                .comTipoPessoaJuridica()
                .build());
    }

    public PagamentoBuilder comPagadorMenorDeIdade() {
        return comPagador(PagadorBuilder.builder()
                .comTipoPessoaFisica()
                .comDesesseteAnos()
                .build());
    }

    public PagamentoBuilder comPagadorDeDesoitoAnosDeIdade() {
        return comPagador(PagadorBuilder.builder()
                .comTipoPessoaFisica()
                .comDesoitoAnos()
                .build());
    }

    public PagamentoBuilder comQrCode(String qrCode) {
        this.qrCode = qrCode;
        return this;
    }

    public PagamentoBuilder comAlgumQrCode() {
        return comQrCode("101md90m1d9m0d129dm09m1");
    }

    public PagamentoBuilder comNenhumQrCode() {
        return comQrCode(null);
    }

    public PagamentoBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public PagamentoBuilder comMaisDeQuinhentosDeValor() {
        return comValor(BigDecimal.valueOf(520.00));
    }

    public PagamentoBuilder comQuinhentosDeValor() {
        return comValor(BigDecimal.valueOf(500.00));
    }

    public TransacaoPagamento build() {
        return new TransacaoPagamento(pagador, qrCode, valor);
    }
}
