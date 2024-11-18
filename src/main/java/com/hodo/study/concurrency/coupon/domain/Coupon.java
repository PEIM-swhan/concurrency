package com.hodo.study.concurrency.coupon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId;
    private int quantity;

    @Version
    private Long version;

    public Coupon(Long couponId, int quantity) {
        this.couponId = couponId;
        this.quantity = quantity;
    }

    public void decrease(int quantity) {
        if (this.quantity - quantity < 0) {
            throw new IllegalStateException();
        }

        this.quantity -= quantity;
    }
}
