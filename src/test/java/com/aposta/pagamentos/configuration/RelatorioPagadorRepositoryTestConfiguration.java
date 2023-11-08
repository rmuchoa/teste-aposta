package com.aposta.pagamentos.configuration;

import com.aposta.pagamentos.repository.RelatorioPagadorCache;
import com.aposta.pagamentos.repository.RelatorioPagadorRepository;
import com.aposta.pagamentos.repository.RelatorioPagadorRepositoryTest;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class RelatorioPagadorRepositoryTestConfiguration {

    @Bean
    @Primary
    public RelatorioPagadorCache relatorioPagadorCache() {
        return Mockito.mock(RelatorioPagadorCache.class);
    }

}
