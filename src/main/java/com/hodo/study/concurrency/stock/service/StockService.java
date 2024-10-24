package com.hodo.study.concurrency.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hodo.study.concurrency.domain.Stock;
import com.hodo.study.concurrency.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	@Transactional
	public void decrease(final Long id, final int quantity) {
		final Stock stock = stockRepository.findById(id).orElseThrow();
		stock.decrease(quantity);
	}
}
