package com.aposta.pagamentos.cache;

import com.aposta.pagamentos.business.pagador.entity.Pagador;
import com.aposta.pagamentos.business.pagador.entity.PagadorPessoaFisicaBuilder;
import com.aposta.pagamentos.business.relatorio.entity.RelatorioPagador;
import com.aposta.pagamentos.business.relatorio.entity.RelatorioPagadorBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RelatorioPagadorCacheTest {

    @Autowired
    private RelatorioPagadorCache cache;

    private String cpf;

    @BeforeEach
    public void setUp() {
        cpf = "12312312300";
        RelatorioPagador relatorio = RelatorioPagadorBuilder.buildRelatorioComPagadorComCpf(cpf);
        RelatorioPagadorCache.getCache().put(cpf, relatorio);
    }

    @Test
    public void deveRetornarTrueQuandoChecarQuaJaExisteUmRelatorioParaPagador() {
        Pagador pagador = PagadorPessoaFisicaBuilder.builder()
                .comCpf(cpf)
                .comDesoitoAnos()
                .build();

        assertTrue(cache.jaExisteRelatorioPagador(pagador),
                "Era esperado que a cache já conteria um relatorio para o cpf do pagador.");
    }

    @Test
    public void deveRetornarRelatorioExistenteQuandoBuscarRelatorioDoPagadorInformado() {
        Pagador pagador = PagadorPessoaFisicaBuilder.builder()
                .comCpf(cpf)
                .comDesoitoAnos()
                .build();

        RelatorioPagador relatorio = cache.buscaRelatorio(pagador);

        assertEquals(pagador.getID(), relatorio.getPagador().getID());
    }

    @Test
    public void deveIncluirNovoRelatorioEmCacheChaveandoAtravesDoCpfDoPagador() {
        String novoCpf = "11122233300";
        RelatorioPagador novoRelatorio = RelatorioPagadorBuilder.buildRelatorioComPagadorComCpf(novoCpf);

        RelatorioPagador relatorioAnterior = cache.registraRelatorio(novoCpf, novoRelatorio);

        assertNull(relatorioAnterior,
                "Era esperado que o novo relatório não estivesse substituindo nenhum relatório anterior");
        assertTrue(RelatorioPagadorCache.getCache().containsKey(novoCpf),
                "Era esperado que um novo relatório fosse encontrado através do novo cpf consultado");
        assertEquals(novoRelatorio, RelatorioPagadorCache.getCache().get(novoCpf));

    }

}
