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

	// Pessimistic Lock
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("select s from Stock s where s.id=:id")
	Stock findByIdWithPessimisticLock(@Param("id") Long id);

	// Optimistic Lock
	@Lock(value = LockModeType.OPTIMISTIC)
	@Query("select s from Stock s where s.id=:id")
	Stock findByIdWithOptimisticLock(@Param("id") Long id);

	// NamedLock 획득 시도
	@Query(value = "SELECT get_lock(:key, 3000)", nativeQuery = true)
	void getLock(@Param("key") String key);

	// NamedLock 반납
	@Query(value = "SELECT release_lock(:key)", nativeQuery = true)
	void releaseLock(@Param("key") String key);


}
