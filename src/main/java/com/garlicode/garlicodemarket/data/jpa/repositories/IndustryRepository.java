package com.garlicode.garlicodemarket.data.jpa.repositories;

import com.garlicode.garlicodemarket.data.jpa.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {

    Optional<Industry> findByLabel(String label);
}
