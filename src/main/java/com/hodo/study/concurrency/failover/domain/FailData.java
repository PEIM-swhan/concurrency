package com.hodo.study.concurrency.failover.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FailData {

	private String id;
	private String title;
	private String content;
}
