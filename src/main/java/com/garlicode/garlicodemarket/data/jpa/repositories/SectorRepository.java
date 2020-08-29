package com.garlicode.garlicodemarket.data.jpa.repositories;

import com.garlicode.garlicodemarket.data.jpa.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    Optional<Sector> findByLabel(String label);
}
