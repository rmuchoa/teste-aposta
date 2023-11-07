package com.aposta.pagamentos.business.validator;

import com.aposta.pagamentos.business.entity.PagamentoBuilder;
import com.aposta.pagamentos.business.exception.TransacaoNaoPermitidaException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransacaoPagamentoValidatorTest {

    @Autowired
    private TransacaoPagamentoValidator validator;

    @Test
    public void shouldThrowExceptionWhenPayerIsACompany() {
        TransacaoNaoPermitidaException thrown = assertThrows(
                TransacaoNaoPermitidaException.class,
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoPessoaJuridica()),
                "Expected validar(transacao) to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Pagador não é pessoa física"));
    }

    @Test
    public void shouldThrowExceptionWhenPayerHasAgeLowerThanEighteen() {
        TransacaoNaoPermitidaException thrown = assertThrows(
                TransacaoNaoPermitidaException.class,
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoMenorDeIdade()),
                "Expected validar(transacao) to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Pagador é menor de Idade"));
    }

    @Test
    public void shouldThrowExceptionWhenPaymentHasNoQRCode() {
        TransacaoNaoPermitidaException thrown = assertThrows(
                TransacaoNaoPermitidaException.class,
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoComNenhumQRCode()),
                "Expected validar(transacao) to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Pagamento não tem qrCode"));
    }

    @Test
    public void shouldThrowExceptionWhenPaymentHasValueGreaterThan500() {
        TransacaoNaoPermitidaException thrown = assertThrows(
                TransacaoNaoPermitidaException.class,
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoComValorQuinhentosMais()),
                "Expected validar(transacao) to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Valor acima de R$500.00"));
    }

    @Test
    public void shouldNeverThrowExceptionWhenPaymentIsValid() {
        assertDoesNotThrow(
                () -> validator.validar(
                        PagamentoBuilder.buildPagamentoValido()));
    }

}
