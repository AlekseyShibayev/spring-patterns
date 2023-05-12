package com.company.app.exchangerate.domain.repository;

import com.company.app.exchangerate.domain.entity.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository extends CrudRepository<ExchangeRate, Long> {

	Optional<ExchangeRate> findFirstByOrderByCreationDateDesc();

	List<ExchangeRate> findAll();
}