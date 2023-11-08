package com.aposta.pagamentos.business.validator;

import com.aposta.pagamentos.business.entity.*;
import com.aposta.pagamentos.business.exception.TransacaoNaoPermitidaException;

import com.aposta.pagamentos.configuration.TransacaoPagamentoValidatorTestConfiguration;
import com.aposta.pagamentos.repository.RelatorioPagadorCache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TransacaoPagamentoValidatorTestConfiguration.class)
public class TransacaoPagamentoValidatorTest {

    private static final String MENSAGEM_PADRAO_ASSERT_THROWS = "Era esperado que a chamada validar(transacao) lancasse uma excecao, mas não ocorreu";

    @Autowired
    private TransacaoPagamentoValidator validator;

    @Autowired
    private RelatorioPagadorCache relatorioPagadorCache;

    @Test
    public void deveLancarExcecaoTransacaoNaoPermitidaQuandoPagadorForUmaPessoaJuridica() {
        TransacaoNaoPermitidaException thrown = assertThrows(
                TransacaoNaoPermitidaException.class,
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoPessoaJuridica()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_PAGADOR_NAO_PESSOA_FISICA));
    }

    @Test
    public void deveLancarExcecaoTransacaoNaoPagaQuandoPagadorTiverIdadeMenorDeDesoitoAnos() {
        TransacaoNaoPermitidaException thrown = assertThrows(
                TransacaoNaoPermitidaException.class,
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoMenorDeIdade()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_PAGADOR_MENOR_DE_IDADE));
    }

    @Test
    public void deveLancarExcecaoTransacaoNaoPermitidaQuandoPagamentoNaoTiverQRCode() {
        TransacaoNaoPermitidaException thrown = assertThrows(
                TransacaoNaoPermitidaException.class,
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoComNenhumQRCode()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_PAGAMENTO_SEM_QR_CODE));
    }

    @Test
    public void deveLancarExcecaoTransacaoNaoPermitidaQuandoPagamentoTiverValorMaiorQueLimiteTransacionalDeQuinhentosReais() {
        TransacaoNaoPermitidaException thrown = assertThrows(
                TransacaoNaoPermitidaException.class,
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoComValorAcimaDeQuinhentos()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_PAGAMENTO_VALOR_ACIMA_QUINHENTOS_REAIS));
    }

    @Test
    public void deveLancarExcecaoQuandoValorPagamentoExcederLimiteGlobalPorContaConsiderandoAcumuladoExistente() {
        String cpf = "12312312300";
        RelatorioPagador relatorio = RelatorioPagadorBuilder.buildRelatorioLimiteAtingidoPagadorComCpf(cpf);

        relatorioPagadorCache.registraRelatorio(cpf, relatorio);

        TransacaoNaoPermitidaException thrown = assertThrows(
                TransacaoNaoPermitidaException.class,
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoComPagadorComCpf(cpf)),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_LIMITE_GERAL_POR_PAGADOR_DE_TRES_MIL_REAIS_ATINGIDO));
    }

    @Test
    public void nuncaDeveLancarExcecaoQuandoPagamentoForValido() {
        assertDoesNotThrow(() -> validator.validar(
                PagamentoBuilder.buildPagamentoValido()));
    }

}
