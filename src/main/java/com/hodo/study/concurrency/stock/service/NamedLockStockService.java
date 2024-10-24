package com.hodo.study.concurrency.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hodo.study.concurrency.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NamedLockStockService {

	private final StockService stockService;
	// private final StockLockRepository lockRepository;
	private final StockRepository lockRepository;


	// NamedLock과 하위 트랙잭션은 별도로 동작해야 함
	@Transactional
	public void decrease(final Long id, final int quantity) {
		try {
			lockRepository.getLock(id.toString());
			stockService.decrease(id, quantity);
		} finally {
			lockRepository.releaseLock(id.toString());
		}
	}
}
