package com.aposta.validator;

import com.aposta.exception.TransacaoNaoPermitidaException;
import com.aposta.model.TransacaoPagamento;

import java.math.BigDecimal;

public class TransacaoPagamentoValidator {

    public static final BigDecimal QUINHENTOS_REAIS = BigDecimal.valueOf(500.00);

    public void validar(TransacaoPagamento transacao) {
        validarPagadorMaiorDeIdade(transacao);
        validarPagadorPessoaFisica(transacao);
        validarPagamentoMenorOuIgualAQuinhentosReais(transacao);
    }

    private void validarPagadorMaiorDeIdade(TransacaoPagamento transacao) {
        if (transacao.ehPagadorMenorDeIdade()) {
            throw new TransacaoNaoPermitidaException("Pagador menor de Idade");
        }
    }

    private void validarPagadorPessoaFisica(TransacaoPagamento transacao) {
        if (transacao.naoEhPessoaFisica()) {
            throw new TransacaoNaoPermitidaException("Não é pessoa física");
        }
    }

    private void validarPagamentoMenorOuIgualAQuinhentosReais(TransacaoPagamento transacao) {
        if (transacao.ehValorAcimaDe(QUINHENTOS_REAIS)) {
            throw new TransacaoNaoPermitidaException("Valor acima de R$500.00");
        }
    }

}
