package com.hodo.study.concurrency.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;
	private int quantity;

	public Stock(final Long productId, final int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public void decrease(final int quantity) {
		if (this.quantity - quantity < 0) {
			throw new IllegalStateException();
		}
		this.quantity -= quantity;
	}
}
