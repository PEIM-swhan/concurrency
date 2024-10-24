package com.hodo.study.concurrency.stock.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SynchronizedStockService {

	private final StockService StockService;

	// @Transactions 선언 시, 하위 트랙잭션이 상위로 묶이기 때문에 동시성 제어 불가능

	public synchronized void decrease(final Long id, final int quantity) {
		StockService.decrease(id, quantity);
	}
}
