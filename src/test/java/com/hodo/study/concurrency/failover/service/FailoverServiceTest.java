package com.hodo.study.concurrency.failover.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hodo.study.concurrency.failover.domain.FailData;
import com.hodo.study.concurrency.failover.repository.FailOverRepository;
import com.hodo.study.concurrency.stock.domain.Stock;

@SpringBootTest
class FailoverServiceTest {

	@Autowired
	private FailoverService failoverService;

	@Autowired
	private FailOverRepository failOverRepository;

	@Test
	@DisplayName("논 컨커런시 컬렉션에 데이터 넣음")
	public void decreaseHundredTest () throws InterruptedException {
		int threadCount = 100;
		final CountDownLatch latch = new CountDownLatch(threadCount);

		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		int id = 1;


		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					FailData failData = new FailData("id", "title", "content");
					failoverService.save(failData);

				}finally {
					latch.countDown();
				}

			});
		}

		latch.await();

		 Assertions.assertEquals(failOverRepository.count(), 100);
	}

}
