package com.garlicode.garlicodemarket.services;

import com.garlicode.garlicodemarket.data.jpa.entity.Currency;
import com.garlicode.garlicodemarket.data.jpa.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository repository;

    public Optional<Currency> getCurrency(String isoCode) {
        return this.repository.findById(isoCode);
    }

    public Currency addCurrency(String isoCode) {
        Currency currency = new Currency();
        currency.setIsoCode(isoCode);
        return this.repository.save(currency);
    }

    public List<Currency> findAll() {
        return this.repository.findAll();
    }
}
