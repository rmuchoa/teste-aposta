package com.aposta.pagamentos.business.pagador.validator;

import com.aposta.pagamentos.business.pagador.entity.PagadorPessoaFisicaBuilder;
import com.aposta.pagamentos.business.pagador.entity.PagadorPessoaJuridicaBuilder;
import com.aposta.pagamentos.business.pagador.exception.PagadorNaoAutorizadoException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PagadorValidatorTest {

    private static final String MENSAGEM_PADRAO_ASSERT_THROWS = "Era esperado que a chamada validar(pagador) lancasse uma excecao, mas não ocorreu";

    @Autowired
    private PagadorValidator validator;

    @Test
    public void deveLancarExcecaoPagadorNaoAutorizadoQuandoPagadorForUmaPessoaJuridica() {
        PagadorNaoAutorizadoException thrown = assertThrows(
                PagadorNaoAutorizadoException.class,
                () -> validator.validar(
                        PagadorPessoaJuridicaBuilder.buildAlgumPagador()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(PagadorValidator.MENSAGEM_PAGADOR_NAO_PESSOA_FISICA));
    }

    @Test
    public void deveLancarExcecaoTransacaoNaoPagaQuandoPagadorTiverIdadeMenorDeDesoitoAnos() {
        PagadorNaoAutorizadoException thrown = assertThrows(
                PagadorNaoAutorizadoException.class,
                () -> validator.validar(
                        PagadorPessoaFisicaBuilder.buildPagadorMenorDeIdade()),
                MENSAGEM_PADRAO_ASSERT_THROWS
        );

        assertTrue(thrown.getMessage()
                .contains(PagadorValidator.MENSAGEM_PAGADOR_MENOR_DE_IDADE));
    }

    @Test
    public void nuncaDeveLancarExcecaoQuandoPagadorForValido() {
        assertDoesNotThrow(() -> validator.validar(
                PagadorPessoaFisicaBuilder.buildPagadorValido()));
    }

}
