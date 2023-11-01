package com.aposta.service;

import com.aposta.exception.TransacaoNaoPermitidaException;
import com.aposta.model.TransacaoPagamento;
import com.aposta.validator.TransacaoPagamentoValidator;

public class TransacaoPagamentoService {

    public void efetuarPagamento(TransacaoPagamento transacao) {
        try {
            TransacaoPagamentoValidator validator = new TransacaoPagamentoValidator();
            validator.validar(transacao);

            System.out.println("Pagamento realizado com sucesso!");

        } catch (TransacaoNaoPermitidaException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
