package com.hodo.study.concurrency.stock.service;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hodo.study.concurrency.stock.domain.Stock;
import com.hodo.study.concurrency.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

	private final StockRepository stockRepository;

	@Transactional
	@Retryable(
		retryFor = {OptimisticLockingFailureException.class},	// 낙관적 잠금 관련 Exception
		maxAttempts = 1000,		// 최대 재시도 횟수
		backoff = @Backoff(100)		// 재시도 간격 ms단위
	)
	public void decrease(final Long id, final int quantity) {
		final Stock stock = stockRepository.findByIdWithOptimisticLock(id);
		stock.decrease(quantity);
	}
}
