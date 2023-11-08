package com.aposta.pagamentos.repository;

import com.aposta.pagamentos.business.entity.*;
import com.aposta.pagamentos.configuration.RelatorioPagadorRepositoryTestConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(RelatorioPagadorRepositoryTestConfiguration.class)
public class RelatorioPagadorRepositoryTest {

    private static final Boolean NAO_TEM_RELATORIO_AINDA = Boolean.FALSE;
    private static final Boolean JA_TEM_RELATORIO = Boolean.TRUE;
    private static final Integer UMA_VEZ = 1;

    @Autowired
    private RelatorioPagadorRepository repository;

    @MockBean
    private RelatorioPagadorCache cache;

    private ArgumentCaptor<RelatorioPagador> relatorioCaptor;

    @BeforeEach
    public void setUp() {
        relatorioCaptor = ArgumentCaptor.forClass(RelatorioPagador.class);
    }

    @Test
    public void deveCriarNovoRelatorioRegistrandoEleQuandoAindaNaoHouverRelatorioEmCache() {
        TransacaoPagamento pagamento = PagamentoBuilder.buildPagamentoValido();
        Pagador pagador = pagamento.getPagador();

        when(cache.jaExisteRelatorioPagador(eq(pagador))).thenReturn(NAO_TEM_RELATORIO_AINDA);

        repository.registrarPagamento(pagamento);

        verify(cache, times(UMA_VEZ))
                .registraRelatorio(eq(pagador.getID()), relatorioCaptor.capture());

        assertEquals(pagador.getID(), relatorioCaptor.getValue().getPagador().getID());
        assertEquals(pagamento.getValor(), relatorioCaptor.getValue().getTotalPago());
    }

    @Test
    public void deveBuscarRelatorioExistenteAcrescentandoValorAoTotalizadorEncontrado() {
        BigDecimal valorNovo = BigDecimal.valueOf(150.0);
        TransacaoPagamento pagamento = PagamentoBuilder.buildPagamentoComValor(valorNovo);

        when(cache.jaExisteRelatorioPagador(eq(pagamento.getPagador())))
                .thenReturn(JA_TEM_RELATORIO);

        BigDecimal valorExistente = BigDecimal.valueOf(220.0);
        RelatorioPagador relatorio = RelatorioPagadorBuilder.buildRelatorioComTotalJaPago(valorExistente);

        when(cache.buscaRelatorio(eq(pagamento.getPagador())))
                .thenReturn(relatorio);

        repository.registrarPagamento(pagamento);

        BigDecimal valoresSomados = valorNovo.add(valorExistente);
        assertEquals(valoresSomados, relatorio.getTotalPago());
    }

}
