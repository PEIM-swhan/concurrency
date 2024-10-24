package com.hodo.study.concurrency.stock.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hodo.study.concurrency.stock.domain.Stock;
import com.hodo.study.concurrency.stock.repository.StockRepository;
import com.hodo.study.concurrency.stock.service.SynchronizedStockService;

@SpringBootTest
public class SynchronizedStockServiceTest {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private SynchronizedStockService synchronizedStockService;

	@BeforeEach
	public void init(){
		stockRepository.deleteAll();

		Stock stock = new Stock(1L, 100);
		stockRepository.saveAndFlush(stock);
	}

	/**
	 * @Transactions과 synchronized를 같이 사용하는 경우 AOP 기반의 프록시 패턴구현으로 인해 동기화 제어 안됌
	 * @Transactions을 제거하거나, 같은 Layer service에서 호출하는 메서드에 synchronized를 선언해서 동기화 처리 가능
	 */
	@Test
	@DisplayName("동시에 출고 요청 100건 테스트")
	public void decreaseHundredTest () throws InterruptedException {
		int threadCount = 100;
		final CountDownLatch latch = new CountDownLatch(threadCount);

		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					synchronizedStockService.decrease(1L, 1);
				}finally {
					latch.countDown();
				}

			});
		}

		latch.await();
		Stock stock = stockRepository.findById(1L).orElseThrow();

		Assertions.assertEquals(stock.getQuantity(), 0);
	}
}
