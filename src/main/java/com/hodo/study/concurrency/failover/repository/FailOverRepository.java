package com.hodo.study.concurrency.failover.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class FailOverRepository {

	private final List<String> inMemoryDB = Collections.synchronizedList(new ArrayList<>());

	public void save(final String data) {
		 inMemoryDB.add(data);
	}

	 public int count() {
	 	return inMemoryDB.size();
	 }

	public String print() {
		return inMemoryDB.toString();
	}
}
