package com.aposta.pagamentos.business.transacao.entity;

import com.aposta.pagamentos.business.pagador.entity.Pagador;
import com.aposta.pagamentos.business.pagador.entity.PagadorPessoaFisica;

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

    public boolean temPagadorMenorDeIdade() {
        return pagador instanceof PagadorPessoaFisica &&
                ((PagadorPessoaFisica) pagador).ehMenorDeIdade();
    }

    public boolean temPagador() {
        return pagador != null;
    }

    public boolean naoTemQRCode() {
        return qrCode == null;
    }

    public boolean ehValorAcimaDe(BigDecimal comparavel) {
        return valor.compareTo(comparavel) > 0;
    }

}
