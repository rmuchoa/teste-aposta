package com.aposta.pagamentos.configuration;

import com.aposta.pagamentos.repository.RelatorioPagadorCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TransacaoPagamentoValidatorTestConfiguration {

    @Bean
    @Primary
    public RelatorioPagadorCache relatorioPagadorCache() {
        return new RelatorioPagadorCache();
    }

}
