package com.garlicode.garlicodemarket.services;

import com.garlicode.garlicodemarket.data.jpa.entity.Industry;
import com.garlicode.garlicodemarket.data.jpa.repositories.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndustryService {

    @Autowired
    private IndustryRepository repository;

    public Optional<Industry> getIndustryById(long id) {
        return this.repository.findById(id);
    }

    public Optional<Industry> getIndustryByLabel(String label) {
        return this.repository.findByLabel(label);
    }

    public Industry addIndustry(String label) {
        Industry industry = new Industry();
        industry.setLabel(label);
        return this.repository.save(industry);
    }

    public List<Industry> findAll() {
        return this.repository.findAll();
    }
}
