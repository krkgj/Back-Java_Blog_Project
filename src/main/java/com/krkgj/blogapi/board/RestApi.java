package com.krkgj.blogapi.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class RestApi {
	
	@GetMapping("/test")
	public void hellow() 
	{
		System.out.println("daweeweaew");
	}

}
