package com.hodo.study.concurrency.coupon.service;

import com.hodo.study.concurrency.coupon.domain.Coupon;
import com.hodo.study.concurrency.coupon.repository.CouponRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class SynchronizedCouponServiceTest {

    @Autowired
    private SynchronizedCouponService synchronizedCouponService;
    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    public void init() {
        couponRepository.deleteAll();

        couponRepository.saveAndFlush(new Coupon(1L, 100));
    }

    @Test
    @DisplayName("쿠폰 감소 요청 동시 100건 테스트")
    public void decreaseHundredTest () throws InterruptedException {
        int threadCount = 100;
        final CountDownLatch latch = new CountDownLatch(threadCount);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    synchronizedCouponService.decrease(1L, 1);
                }finally {
                    latch.countDown();
                }

            });
        }

        latch.await();
        Coupon coupon = couponRepository.findById(1L).orElseThrow();

        Assertions.assertEquals(coupon.getQuantity(), 0);
    }

}
