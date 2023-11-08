package com.aposta.pagamentos.business.transacao.validator;

import com.aposta.pagamentos.business.pagador.validator.PagadorValidator;
import com.aposta.pagamentos.business.relatorio.entity.RelatorioPagador;
import com.aposta.pagamentos.business.transacao.entity.TransacaoPagamento;
import com.aposta.pagamentos.business.transacao.exception.TransacaoNaoPermitidaException;
import com.aposta.pagamentos.cache.RelatorioPagadorCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransacaoPagamentoValidator {

    protected static final BigDecimal LIMITE_TRANSACIONAL = BigDecimal.valueOf(500.00);
    public static final String MENSAGEM_PAGAMENTO_SEM_QR_CODE = "Pagamento não tem qrCode";
    public static final String MENSAGEM_PAGAMENTO_VALOR_ACIMA_QUINHENTOS_REAIS = "Valor acima de R$500.00";
    public static final String MENSAGEM_LIMITE_GERAL_POR_PAGADOR_DE_TRES_MIL_REAIS_ATINGIDO = "Atingiu limite geral de R$3000.00 por Pagador";

    @Autowired
    private PagadorValidator pagadorValidator;

    @Autowired
    private RelatorioPagadorCache cache;

    public void validar(TransacaoPagamento transacao) {
        validarPagador(transacao);
        validarPagamentoComQRCode(transacao);
        validarPagamentoMenorQueLimiteIndividual(transacao);
        validarLimiteGeralNaoAtingido(transacao);
    }

    private void validarPagador(TransacaoPagamento transacao) {
        if (transacao.temPagador()) {
            pagadorValidator.validar(transacao.getPagador());
        }
    }

    private void validarPagamentoComQRCode(TransacaoPagamento transacao) {
        if (transacao.naoTemQRCode()) {
            throw new TransacaoNaoPermitidaException(MENSAGEM_PAGAMENTO_SEM_QR_CODE);
        }
    }

    private void validarPagamentoMenorQueLimiteIndividual(TransacaoPagamento transacao) {
        if (transacao.ehValorAcimaDe(LIMITE_TRANSACIONAL)) {
            throw new TransacaoNaoPermitidaException(MENSAGEM_PAGAMENTO_VALOR_ACIMA_QUINHENTOS_REAIS);
        }
    }

    private void validarLimiteGeralNaoAtingido(TransacaoPagamento transacao) {
        RelatorioPagador relatorio = cache.buscaRelatorio(transacao.getPagador());

        if (relatorio == null) {
            relatorio = new RelatorioPagador(transacao.getPagador());
        }

        if (relatorio.extrapolouLimiteGeralPorConta(transacao.getValor())) {
            throw new TransacaoNaoPermitidaException(MENSAGEM_LIMITE_GERAL_POR_PAGADOR_DE_TRES_MIL_REAIS_ATINGIDO);
        }
    }

}
