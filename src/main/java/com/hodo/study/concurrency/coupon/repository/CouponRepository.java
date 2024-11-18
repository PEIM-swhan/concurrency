package com.hodo.study.concurrency.coupon.repository;

import com.hodo.study.concurrency.coupon.domain.Coupon;
import com.hodo.study.concurrency.stock.domain.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {


    // Pessimistic Lock
    @Lock(value = LockModeType.PESSIMISTIC_WRITE) // FOR UPDATE
    @Query("select c from Coupon c where c.id=:id")
    Coupon findByIdWithPessimisticLock(@Param("id") Long id);

    // Optimistic Lock
    @Lock(value = LockModeType.OPTIMISTIC)
    @Query("select c from Coupon c where c.id=:id")
    Coupon findByIdWithOptimisticLock(@Param("id") Long id);

}
