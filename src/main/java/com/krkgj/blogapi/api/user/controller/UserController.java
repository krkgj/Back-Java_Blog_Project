package com.krkgj.blogapi.api.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
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
	private final 	UserBean 	userBean;
	private final 	UserService userService;
	
	@GetMapping(value = {"", "/"})
	public ResultMessage getUserInfo()
	{
		long userSeq = userBean.getSeq();
		
		UserDto userDto = userService.findUserInfo(userSeq);
		
		return new ResultMessage(ResultCode.RESULT_OK, "findUserInfo", userDto);
	}
}
