package com.hodo.study.concurrency.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ReentrantLockCouponService {

    private final CouponService couponService;
    private final ReentrantLock reentrantLock = new ReentrantLock(true);

    public void decrease(Long id, int quantity) {
        try {
            reentrantLock.lockInterruptibly();
            couponService.decrease(id, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            reentrantLock.unlock();
        }


    }
}
