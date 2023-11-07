package com.aposta.pagamentos.business.validator;

import com.aposta.pagamentos.business.entity.TransacaoPagamento;
import com.aposta.pagamentos.business.exception.TransacaoNaoPermitidaException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransacaoPagamentoValidator {

    public static final BigDecimal QUINHENTOS_REAIS = BigDecimal.valueOf(500.00);

    public void validar(TransacaoPagamento transacao) {
        validarPagadorPessoaFisica(transacao);
        validarPagadorMaiorDeIdade(transacao);
        validarPagamentoComQRCode(transacao);
        validarPagamentoMenorOuIgualAQuinhentosReais(transacao);
    }

    private void validarPagadorPessoaFisica(TransacaoPagamento transacao) {
        if (transacao.temPagadorPessoaJuridica()) {
            throw new TransacaoNaoPermitidaException("Pagador não é pessoa física");
        }
    }

    private void validarPagadorMaiorDeIdade(TransacaoPagamento transacao) {
        if (transacao.temPagadorMenorDeIdade()) {
            throw new TransacaoNaoPermitidaException("Pagador é menor de Idade");
        }
    }

    private void validarPagamentoComQRCode(TransacaoPagamento transacao) {
        if (transacao.naoTemQRCode()) {
            throw new TransacaoNaoPermitidaException("Pagamento não tem qrCode");
        }
    }

    private void validarPagamentoMenorOuIgualAQuinhentosReais(TransacaoPagamento transacao) {
        if (transacao.ehValorAcimaDe(QUINHENTOS_REAIS)) {
            throw new TransacaoNaoPermitidaException("Valor acima de R$500.00");
        }
    }

}
