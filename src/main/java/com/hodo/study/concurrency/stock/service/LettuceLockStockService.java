package com.hodo.study.concurrency.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hodo.study.concurrency.stock.domain.Stock;
import com.hodo.study.concurrency.stock.repository.RedisLockRepository;
import com.hodo.study.concurrency.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LettuceLockStockService {
	private final RedisLockRepository redisLockRepository;

	private final StockService stockService;

	@Transactional
	public void decrease(final Long id, final int quantity) {
		while (!redisLockRepository.lock(id)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		try {
			stockService.decrease(id, quantity);
		} finally {
			redisLockRepository.unLock(id);
		}
	}
}
