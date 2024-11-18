package com.hodo.study.concurrency.coupon.service;

import com.hodo.study.concurrency.coupon.domain.Coupon;
import com.hodo.study.concurrency.coupon.repository.CouponRepository;
import com.hodo.study.concurrency.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockCouponService {

    private final CouponRepository couponRepository;

    @Transactional
    @Retryable(
            retryFor = {OptimisticLockingFailureException.class},	// 낙관적 잠금 관련 Exception
            maxAttempts = 1000,		// 최대 재시도 횟수
            backoff = @Backoff(100)		// 재시도 간격 ms단위
    )
    public void decrease(Long id, int quantity) {
        Coupon coupon = couponRepository.findByIdWithOptimisticLock(id);

        coupon.decrease(quantity);
    }
}
