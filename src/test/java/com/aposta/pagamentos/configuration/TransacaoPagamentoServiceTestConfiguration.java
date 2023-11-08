package com.aposta.pagamentos.configuration;

import com.aposta.pagamentos.cache.RelatorioPagadorCache;
import com.aposta.pagamentos.business.relatorio.repository.RelatorioPagadorRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TransacaoPagamentoServiceTestConfiguration {

    @Bean
    @Primary
    public RelatorioPagadorCache relatorioPagadorCache() {
        return new RelatorioPagadorCache();
    }

    @Bean
    @Primary
    public RelatorioPagadorRepository relatorioPagadorRepository() {
        return Mockito.mock(RelatorioPagadorRepository.class);
    }

}
