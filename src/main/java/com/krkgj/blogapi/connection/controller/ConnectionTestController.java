package com.krkgj.blogapi.connection.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krkgj.blogapi.connection.service.ConnectionTestService;

@RestController("/")
public class ConnectionTestController 
{
	
	@Resource(name="com.krkgj.blogapi.connection.service")
	ConnectionTestService connectionTestService;
	
	@GetMapping("/connection_test")
	public void connectionTest() 
	{
		System.out.println("daweeweaew");
	}

}