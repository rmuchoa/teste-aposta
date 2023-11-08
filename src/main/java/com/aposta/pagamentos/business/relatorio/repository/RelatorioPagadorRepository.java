package com.aposta.pagamentos.business.relatorio.repository;

import com.aposta.pagamentos.business.pagador.entity.Pagador;
import com.aposta.pagamentos.business.relatorio.entity.RelatorioPagador;
import com.aposta.pagamentos.business.transacao.entity.TransacaoPagamento;
import com.aposta.pagamentos.cache.RelatorioPagadorCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelatorioPagadorRepository {

    @Autowired
    private RelatorioPagadorCache cache;

    public void registrarPagamento(TransacaoPagamento pagamento) {
        Pagador pagador = pagamento.getPagador();

        if (cache.jaExisteRelatorioPagador(pagador)) {
            RelatorioPagador relatorio = cache.buscaRelatorio(pagador);
            relatorio.acrescentarValor(pagamento.getValor());
        } else {
            RelatorioPagador relatorio = new RelatorioPagador(pagador);
            relatorio.acrescentarValor(pagamento.getValor());
            cache.registraRelatorio(pagador.getID(), relatorio);
        }
    }

}
