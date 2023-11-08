package com.aposta.pagamentos.business.pagador.validator;

import com.aposta.pagamentos.business.pagador.entity.Pagador;
import com.aposta.pagamentos.business.pagador.exception.PagadorNaoAutorizadoException;
import com.aposta.pagamentos.business.transacao.exception.TransacaoNaoPermitidaException;
import org.springframework.stereotype.Component;

@Component
public class PagadorValidator {

    public static final String MENSAGEM_PAGADOR_NAO_PESSOA_FISICA = "Pagador não é pessoa física";
    public static final String MENSAGEM_PAGADOR_MENOR_DE_IDADE = "Pagador é menor de Idade";

    public void validar(Pagador pagador) {
        validarPagadorPessoaFisica(pagador);
        validarPagadorMaiorDeIdade(pagador);
    }

    private void validarPagadorPessoaFisica(Pagador pagador) {
        if (pagador.ehPessoaJuridica()) {
            throw new PagadorNaoAutorizadoException(MENSAGEM_PAGADOR_NAO_PESSOA_FISICA);
        }
    }

    private void validarPagadorMaiorDeIdade(Pagador pagador) {
        if (pagador.ehMenorDeIdade()) {
            throw new PagadorNaoAutorizadoException(MENSAGEM_PAGADOR_MENOR_DE_IDADE);
        }
    }

}
