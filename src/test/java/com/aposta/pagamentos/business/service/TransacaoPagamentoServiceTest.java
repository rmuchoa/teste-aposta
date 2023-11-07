package com.aposta.pagamentos.business.service;

import com.aposta.pagamentos.business.entity.PagamentoBuilder;
import com.aposta.pagamentos.business.exception.TransacaoNaoPagaException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransacaoPagamentoServiceTest {

    @Autowired
    private TransacaoPagamentoService service;

    @Test
    public void shouldThrowNotPaidExceptionWhenPayerIsACompany() {
        TransacaoNaoPagaException thrown = assertThrows(
                TransacaoNaoPagaException.class,
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoPessoaJuridica()),
                "Expected efetuarPagamento(transacao) to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Pagador não é pessoa física"));
    }

    @Test
    public void shouldThrowExceptionWhenPayerHasAgeLowerThanEighteen() {
        TransacaoNaoPagaException thrown = assertThrows(
                TransacaoNaoPagaException.class,
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoMenorDeIdade()),
                "Expected efetuarPagamento(transacao) to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Pagador é menor de Idade"));
    }

    @Test
    public void shouldThrowExceptionWhenPaymentHasNoQRCode() {
        TransacaoNaoPagaException thrown = assertThrows(
                TransacaoNaoPagaException.class,
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoComNenhumQRCode()),
                "Expected efetuarPagamento(transacao) to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Pagamento não tem qrCode"));
    }

    @Test
    public void shouldThrowExceptionWhenPaymentHasValueGreaterThan500() {
        TransacaoNaoPagaException thrown = assertThrows(
                TransacaoNaoPagaException.class,
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoComValorQuinhentosMais()),
                "Expected efetuarPagamento(transacao) to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Valor acima de R$500.00"));
    }

    @Test
    public void shouldNeverThrowExceptionWhenPaymentIsValid() {
        assertDoesNotThrow(
                () -> service.efetuarPagamento(
                        PagamentoBuilder.buildPagamentoValido()));
    }

}
