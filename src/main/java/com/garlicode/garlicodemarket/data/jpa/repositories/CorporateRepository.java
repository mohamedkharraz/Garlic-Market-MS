package com.garlicode.garlicodemarket.data.jpa.repositories;

import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateRepository extends JpaRepository<Corporate, String> {
	
	@Query("SELECT DISTINCT c.country FROM Corporate c ORDER BY c.country")
	List<String> getDistinctCountries();
}
