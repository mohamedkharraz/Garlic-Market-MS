package com.garlicode.garlicodemarket.services;

import com.garlicode.garlicodemarket.data.jpa.entity.Sector;
import com.garlicode.garlicodemarket.data.jpa.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorService {

    @Autowired
    private SectorRepository repository;

    public Optional<Sector> getSectorById(long id) {
        return this.repository.findById(id);
    }

    public Optional<Sector> getSectorByLabel(String label) {
        return this.repository.findByLabel(label);
    }

    public Sector addSector(String label) {
        Sector sector = new Sector();
        sector.setLabel(label);
        return this.repository.save(sector);
    }

    public List<Sector> findAll() {
        return this.repository.findAll();
    }
}
