package com.aposta.pagamentos.business.service;

import com.aposta.pagamentos.business.entity.TransacaoPagamento;
import com.aposta.pagamentos.business.exception.TransacaoNaoPagaException;
import com.aposta.pagamentos.business.exception.TransacaoNaoPermitidaException;
import com.aposta.pagamentos.business.validator.TransacaoPagamentoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoPagamentoService {

    @Autowired
    private TransacaoPagamentoValidator validator;

    public TransacaoPagamento efetuarPagamento(TransacaoPagamento transacao) {
        try {
            validator.validar(transacao);


            return transacao;

        } catch (TransacaoNaoPermitidaException ex) {
            throw new TransacaoNaoPagaException(ex.getMotivo());
        }
    }

}
