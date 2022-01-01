package com.krkgj.blogapi.api.user.service;



import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.krkgj.blogapi.api.user.dto.UserDto;
import com.krkgj.blogapi.api.user.entity.QUserEntity;
import com.krkgj.blogapi.api.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService 
{
	private final JPAQueryFactory 	query;
	private final UserRepository	userRepository;
	
	public UserDto findUserInfo(long userSeq)
	{
		return UserDto.builder(userRepository.findById(userSeq).get());
	}
}
