package com.hodo.study.concurrency.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hodo.study.concurrency.stock.domain.Stock;

import jakarta.persistence.LockModeType;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("select s from Stock s where s.id=:id")
	Stock findByIdWithPessimisticLock(@Param("id") Long id);

	@Lock(value = LockModeType.OPTIMISTIC)
	@Query("select s from Stock s where s.id=:id")
	Stock findByIdWithOptimisticLock(@Param("id") Long id);


}
