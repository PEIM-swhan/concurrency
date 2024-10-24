package com.hodo.study.concurrency.stock.repository;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {

	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * Redis setnx 명령어를 사용한 lock 획득
	 * lock의 만료시간을 설정할 수 있음 (3초)
	 * lock이 없으면 획득하고 있
	 */
	public Boolean lock(Long key) {
		return redisTemplate.opsForValue()
			.setIfAbsent(key.toString(), "lock", Duration.ofMillis(3000));
	}

	public Boolean unLock(Long key) {
		return redisTemplate.delete(key.toString());
	}

}
