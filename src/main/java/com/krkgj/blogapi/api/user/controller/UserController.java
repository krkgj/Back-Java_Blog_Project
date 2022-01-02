package com.krkgj.blogapi.api.user.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krkgj.blogapi.api.user.bean.UserBean;
import com.krkgj.blogapi.api.user.dto.UserDto;
import com.krkgj.blogapi.api.user.service.UserService;
import com.krkgj.blogapi.framework.result.ResultCode;
import com.krkgj.blogapi.framework.result.ResultMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController 
{
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final 	UserBean 	userBean;
	private final 	UserService userService;
	
	@GetMapping(value = {"", "/"})
	public ResultMessage getUserInfo()
	{
		long userSeq = userBean.getSeq();
		
		UserDto userDto = userService.findUserInfo(userSeq);
		
		return new ResultMessage(ResultCode.RESULT_OK, "findUserInfo", userDto);
	}
	
	@PostMapping(value = { "/auth/signup" })
	private ResultMessage signUp(@RequestBody UserDto userDto, HttpServletResponse res) 
	{
		logger.info("	Sign Up Process Excute!");
        return new ResultMessage(ResultCode.RESULT_OK, userService.signUp(userDto));
	}
	
	@PostMapping(value = { "/auth/login/token" })
	private ResultMessage login(@RequestBody UserDto userDto, HttpServletResponse res) 
	{
		logger.info("	Login Process Excute!");
        return new ResultMessage(ResultCode.RESULT_OK, userService.login(userDto));
	}
}
