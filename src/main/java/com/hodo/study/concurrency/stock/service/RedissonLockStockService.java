package com.hodo.study.concurrency.stock.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hodo.study.concurrency.stock.repository.RedisLockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedissonLockStockService {
	private final RedissonClient redissonClient;

	private final StockService stockService;

	public void decrease(final Long id, final int quantity) {
		RLock lock = redissonClient.getLock(id.toString());

		// 10초간 Lock 획득을 시도하고, 1초간 Lock을 유지한다.
		// 10초 뒤에도 Lock 획득을 하지 못하면 실패로 간주
		try {
			boolean hadLock = lock.tryLock(1, 1, TimeUnit.SECONDS);
			if (!hadLock) {
				System.out.println("lock 획득 실패");
				return;
			}
			stockService.decrease(id, quantity);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}

	}
}
