package com.garlicode.garlicodemarket.services;

import com.garlicode.garlicodemarket.data.jpa.pojo.CorporateData;
import com.garlicode.garlicodemarket.providers.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MarketService {

    @Autowired
    private Function<String, DataProvider> dataProviderFactory;

    public CorporateData getCorporateData(String ticker){
        //DataProvider provider = new YahooProvider(ticker);
        DataProvider provider = dataProviderFactory.apply(ticker);
        provider.execute();
        CorporateData data = new CorporateData(provider);
        return data;
    }
}
