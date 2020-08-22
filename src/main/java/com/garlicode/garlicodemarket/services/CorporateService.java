package com.garlicode.garlicodemarket.services;

import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import com.garlicode.garlicodemarket.data.jpa.repositories.CorporateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorporateService {

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

    public Corporate addNewCorporate(String ticker) {
        Corporate corporate = new Corporate();
        corporate.setTicker(ticker);
        return this.create(corporate);
    }
}
