package com.garlicode.garlicodemarket.services;

import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import com.garlicode.garlicodemarket.data.jpa.pojo.CorporateData;
import com.garlicode.garlicodemarket.data.jpa.repositories.CorporateRepository;
import com.garlicode.garlicodemarket.providers.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CorporateService {

    @Autowired
    private Function<String, DataProvider> dataProviderFactory;
    @Autowired
    private CorporateRepository repo;

    public List<Corporate> findAll() {
        return this.repo.findAll();
    }

    public Corporate create(Corporate corporate) {
        return this.repo.save(corporate);
    }

    public Optional<Corporate> getCorporateByTicker(String ticker) {
        return this.repo.findById(ticker);
    }

    public Corporate addNewCorporate(String ticker) throws Exception {
        DataProvider provider = this.dataProviderFactory.apply(ticker);

        try {
            provider.execute();
        } catch (Exception e) {
            throw e;
        }

        Corporate corporate = new CorporateData(provider).getCorporate();
        return this.create(corporate);
    }

    public Corporate updateCorporate(Corporate corporate, Corporate newCorporate) {
        corporate.setName(newCorporate.getName());
        corporate.setCountry(newCorporate.getCountry());
        corporate.setLogo(newCorporate.getLogo());
        corporate.setCurrency(newCorporate.getCurrency());
        corporate.setSector(newCorporate.getSector());
        corporate.setIndustry(newCorporate.getIndustry());
        return this.repo.save(corporate);
    }
    
    public List<String> getDistinctCountries(){
    	return this.repo.getDistinctCountries();
    }
}
