package com.aposta.pagamentos.cache;

import com.aposta.pagamentos.business.pagador.entity.Pagador;
import com.aposta.pagamentos.business.relatorio.entity.RelatorioPagador;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RelatorioPagadorCache {

    private static Map<String, RelatorioPagador> cache;

    protected static Map<String, RelatorioPagador> getCache() {
        if (cache == null) {
            cache = new HashMap<>();
        }
        return cache;
    }

    public boolean jaExisteRelatorioPagador(Pagador pagador) {
        return getCache().containsKey(pagador.getID());
    }

    public RelatorioPagador buscaRelatorio(Pagador pagador) {
        return getCache().get(pagador.getID());
    }

    public RelatorioPagador registraRelatorio(String id, RelatorioPagador relatorio) {
        return getCache().put(id, relatorio);
    }

}
