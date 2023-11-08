package com.aposta.pagamentos.configuration;

import com.aposta.pagamentos.cache.RelatorioPagadorCache;
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
