package com.garlicode.garlicodemarket.config;

import com.garlicode.garlicodemarket.providers.DataProvider;
import com.garlicode.garlicodemarket.providers.YahooProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.function.Function;

@Configuration
public class GarlicConfiguration {

    @Bean
    public Function<String, DataProvider> dataProviderFactory(){
        return ticker -> createDataProvider(ticker);
    }

    @Bean
    @Scope(value = "prototype")
    public DataProvider createDataProvider(String ticker){
        return new YahooProvider(ticker);
    }
}
