package com.hodo.study.concurrency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hodo.study.concurrency.domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {


}
