package com.aposta.model;

import java.math.BigDecimal;

public class TransacaoPagamento {

    private Pagador pagador;
    private String qrCode;

    private BigDecimal valor;

    public TransacaoPagamento(Pagador pagador, String qrCode, BigDecimal valor) {
        this.pagador = pagador;
        this.qrCode = qrCode;
        this.valor = valor;
    }

    public Pagador getPagador() {
        return pagador;
    }

    public void setPagador(Pagador pagador) {
        this.pagador = pagador;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean ehPagadorMenorDeIdade() {
        return this.pagador.ehMenorDeIdade();
    }

    public boolean naoEhPessoaFisica() {
        return !this.pagador.ehPessoaFisica();
    }

    public boolean ehValorAcimaDe(BigDecimal valorComparavel) {
        return this.valor.compareTo(valorComparavel) > 0;
    }
}
