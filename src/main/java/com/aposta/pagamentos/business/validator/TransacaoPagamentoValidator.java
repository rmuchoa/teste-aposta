package com.aposta.pagamentos.business.validator;

import com.aposta.pagamentos.business.entity.RelatorioPagador;
import com.aposta.pagamentos.business.entity.TransacaoPagamento;
import com.aposta.pagamentos.business.exception.TransacaoNaoPermitidaException;
import com.aposta.pagamentos.repository.RelatorioPagadorCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransacaoPagamentoValidator {

    protected static final BigDecimal LIMITE_TRANSACIONAL = BigDecimal.valueOf(500.00);
    public static final String MENSAGEM_PAGADOR_NAO_PESSOA_FISICA = "Pagador não é pessoa física";
    public static final String MENSAGEM_PAGADOR_MENOR_DE_IDADE = "Pagador é menor de Idade";
    public static final String MENSAGEM_PAGAMENTO_SEM_QR_CODE = "Pagamento não tem qrCode";
    public static final String MENSAGEM_PAGAMENTO_VALOR_ACIMA_QUINHENTOS_REAIS = "Valor acima de R$500.00";
    public static final String MENSAGEM_LIMITE_GERAL_POR_PAGADOR_DE_TRES_MIL_REAIS_ATINGIDO = "Atingiu limite geral de R$3000.00 por Pagador";

    @Autowired
    private RelatorioPagadorCache cache;

    public void validar(TransacaoPagamento transacao) {
        validarPagadorPessoaFisica(transacao);
        validarPagadorMaiorDeIdade(transacao);
        validarPagamentoComQRCode(transacao);
        validarPagamentoMenorQueLimiteIndividual(transacao);
        validarLimiteGeralNaoAtingido(transacao);
    }

    private void validarPagadorPessoaFisica(TransacaoPagamento transacao) {
        if (transacao.temPagadorPessoaJuridica()) {
            throw new TransacaoNaoPermitidaException(MENSAGEM_PAGADOR_NAO_PESSOA_FISICA);
        }
    }

    private void validarPagadorMaiorDeIdade(TransacaoPagamento transacao) {
        if (transacao.temPagadorMenorDeIdade()) {
            throw new TransacaoNaoPermitidaException(MENSAGEM_PAGADOR_MENOR_DE_IDADE);
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
