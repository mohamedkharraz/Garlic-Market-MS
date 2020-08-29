package com.garlicode.garlicodemarket.data.jpa.repositories;

import com.garlicode.garlicodemarket.data.jpa.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
