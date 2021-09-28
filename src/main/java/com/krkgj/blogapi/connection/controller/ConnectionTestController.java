package com.krkgj.blogapi.connection.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krkgj.blogapi.connection.dto.ConnectionTestDTO;
import com.krkgj.blogapi.connection.service.ConnectionTestService;

@RestController
@RequestMapping("/api/connection")
public class ConnectionTestController 
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="com.krkgj.blogapi.connection.service.ConnectionTestService")
	ConnectionTestService connectionTestService;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE } )
	public ResponseEntity<List<ConnectionTestDTO>> connectionTest() 
	{
		List<ConnectionTestDTO> list = connectionTestService.findAll();

		return new ResponseEntity<List<ConnectionTestDTO>>(list, HttpStatus.OK);
	}

}