package com.hodo.study.concurrency.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hodo.study.concurrency.stock.domain.Stock;
import com.hodo.study.concurrency.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {

	private final StockRepository stockRepository;

	@Transactional
	public void decrease(final Long id, final int quantity) {
		final Stock stock = stockRepository.findByIdWithPessimisticLock(id);
		stock.decrease(quantity);
	}
}
