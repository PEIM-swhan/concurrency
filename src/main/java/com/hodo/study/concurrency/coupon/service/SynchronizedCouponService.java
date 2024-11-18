package com.hodo.study.concurrency.coupon.service;

import com.hodo.study.concurrency.coupon.domain.Coupon;
import com.hodo.study.concurrency.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SynchronizedCouponService {

    private final CouponService couponService;

    public synchronized void decrease(Long id, int quantity) {
        couponService.decrease(id, quantity);
    }
}
