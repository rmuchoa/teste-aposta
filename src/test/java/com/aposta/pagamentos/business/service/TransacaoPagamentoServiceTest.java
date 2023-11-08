package com.aposta.pagamentos.business.service;

import com.aposta.pagamentos.business.entity.PagamentoBuilder;
import com.aposta.pagamentos.business.entity.RelatorioPagador;
import com.aposta.pagamentos.business.entity.RelatorioPagadorBuilder;
import com.aposta.pagamentos.business.entity.TransacaoPagamento;
import com.aposta.pagamentos.business.exception.TransacaoNaoPagaException;

import com.aposta.pagamentos.business.validator.TransacaoPagamentoValidator;
import com.aposta.pagamentos.configuration.TransacaoPagamentoServiceTestConfiguration;
import com.aposta.pagamentos.repository.RelatorioPagadorCache;
import com.aposta.pagamentos.repository.RelatorioPagadorRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Import(TransacaoPagamentoServiceTestConfiguration.class)
public class TransacaoPagamentoServiceTest {

    private static final String MENSAGEM_PADRAO_ASSERT_THROWS = "Era esperado que a chamada efetuarPagamento(transacao) lancasse uma excecao, mas não ocorreu";

    private static final Integer UMA_VEZ = 1;

    @Autowired
    private TransacaoPagamentoService service;

    @Autowired
    private RelatorioPagadorCache relatorioPagadorCache;

    @MockBean
    private RelatorioPagadorRepository relatorioPagadorRepository;

    @Test
    public void deveLancarExcecaoTransacaoNaoPagaQuandoPagadorForUmaPessoaJuridica() {
        TransacaoNaoPagaException thrown = assertThrows(
                TransacaoNaoPagaException.class,
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoPessoaJuridica()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_PAGADOR_NAO_PESSOA_FISICA));
    }

    @Test
    public void deveLancarExcecaoTransacaoNaoPagaQuandoPagadoTiverUmaIdadeMenorDeDesoitoAnos() {
        TransacaoNaoPagaException thrown = assertThrows(
                TransacaoNaoPagaException.class,
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoMenorDeIdade()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_PAGADOR_MENOR_DE_IDADE));
    }

    @Test
    public void deveLancarExcecaoTransacaoNaoPagaQuandoPagamentoNaoTiverQRCode() {
        TransacaoNaoPagaException thrown = assertThrows(
                TransacaoNaoPagaException.class,
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoComNenhumQRCode()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_PAGAMENTO_SEM_QR_CODE));
    }

    @Test
    public void deveLancarExcecaoTransacaoNaoPagaQuandoPagamentoTiverValorAcimaDoLimiteTransacionalDeQuinhentosReais() {
        TransacaoNaoPagaException thrown = assertThrows(
                TransacaoNaoPagaException.class,
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoComValorAcimaDeQuinhentos()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_PAGAMENTO_VALOR_ACIMA_QUINHENTOS_REAIS));
    }

    @Test
    public void deveLancarExcecaoTransacaoNaoPagaQuandoValorPagamentoExcederLimiteGlobalPorContaConsiderandoAcumuladoExistente() {
        String cpf = "12312312300";
        RelatorioPagador relatorio = RelatorioPagadorBuilder.buildRelatorioLimiteAtingidoPagadorComCpf(cpf);

        relatorioPagadorCache.registraRelatorio(cpf, relatorio);

        TransacaoNaoPagaException thrown = assertThrows(
                TransacaoNaoPagaException.class,
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoComPagadorComCpf(cpf)),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(TransacaoPagamentoValidator.MENSAGEM_LIMITE_GERAL_POR_PAGADOR_DE_TRES_MIL_REAIS_ATINGIDO));
    }

    @Test
    public void nuncaDeveLancarExcecaoQuandoTransacaoDePagamentoForValida() {
        assertDoesNotThrow(() -> service.efetuarPagamento(
                PagamentoBuilder.buildPagamentoValido()));
    }

    @Test
    public void deveChamarRepositoryParaRegistrarPagamentoQuandoTransacaoPagamentoForValido() {

        TransacaoPagamento pagamento = service.efetuarPagamento(PagamentoBuilder.buildPagamentoValido());

        verify(relatorioPagadorRepository, times(UMA_VEZ))
                .registrarPagamento(eq(pagamento));
    }

}
