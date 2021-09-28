package com.krkgj.blogapi.connection.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krkgj.blogapi.connection.dto.ConnectionTestDTO;
import com.krkgj.blogapi.connection.repository.ConnectionTestRepository;

import lombok.RequiredArgsConstructor;

@Service("com.krkgj.blogapi.connection.service.ConnectionTestService")
@RequiredArgsConstructor
public class ConnectionTestService 
{
	private final ConnectionTestRepository connectionTestRepository;
	
	public List<ConnectionTestDTO> findAll()
	{
		List<ConnectionTestDTO> list = new ArrayList<>();
		connectionTestRepository.findAll().forEach(dto -> list.add(dto));
		
		return list;
	}
	

}
