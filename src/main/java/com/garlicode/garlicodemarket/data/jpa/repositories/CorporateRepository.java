package com.garlicode.garlicodemarket.data.jpa.repositories;

import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateRepository extends JpaRepository<Corporate, String> {
}
