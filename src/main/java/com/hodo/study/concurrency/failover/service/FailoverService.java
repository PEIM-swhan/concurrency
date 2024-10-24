package com.hodo.study.concurrency.failover.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodo.study.concurrency.failover.domain.FailData;
import com.hodo.study.concurrency.failover.repository.FailOverRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FailoverService {

	private final FailOverRepository failOverRepository;

	public void save(FailData failData) {
		ObjectMapper objectMapper = new ObjectMapper();
		String s = null;
		try {
			s = objectMapper.writeValueAsString(failData);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		failOverRepository.save(s);

	}
}
