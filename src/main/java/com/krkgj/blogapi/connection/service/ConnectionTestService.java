package com.krkgj.blogapi.connection.service;

import org.springframework.stereotype.Service;

import com.krkgj.blogapi.connection.repository.ConnectionTestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConnectionTestService 
{
	private final ConnectionTestRepository connectionTestRepository;

}
