package com.aposta.pagamentos.business.transacao.service;

import com.aposta.pagamentos.business.pagador.entity.Pagador;
import com.aposta.pagamentos.business.pagador.exception.PagadorNaoAutorizadoException;
import com.aposta.pagamentos.business.transacao.entity.TransacaoPagamento;
import com.aposta.pagamentos.business.transacao.exception.TransacaoNaoPagaException;
import com.aposta.pagamentos.business.transacao.exception.TransacaoNaoPermitidaException;
import com.aposta.pagamentos.business.transacao.validator.TransacaoPagamentoValidator;
import com.aposta.pagamentos.business.relatorio.repository.RelatorioPagadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoPagamentoService {

    @Autowired
    private TransacaoPagamentoValidator validator;
    @Autowired
    private RelatorioPagadorRepository repository;

    public TransacaoPagamento efetuarPagamento(TransacaoPagamento transacao) {
        try {

            validator.validar(transacao);
            repository.registrarPagamento(transacao);

            return transacao;

        } catch (TransacaoNaoPermitidaException ex) {
            throw new TransacaoNaoPagaException(ex.getMotivo());
        } catch (PagadorNaoAutorizadoException ex) {
            throw new TransacaoNaoPagaException(ex.getMotivo());
        }
    }

}
