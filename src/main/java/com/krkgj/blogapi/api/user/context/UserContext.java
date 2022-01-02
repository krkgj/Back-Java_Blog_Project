package com.krkgj.blogapi.api.user.context;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.krkgj.blogapi.api.user.entity.UserEntity;


public class UserContext extends User
{
	private static final long serialVersionUID = 1L;
	
	private final UserEntity userEntity;

	public UserContext(UserEntity userEntity) 
	{
		super(userEntity.getId(), userEntity.getPassword(), Collections.singleton(new SimpleGrantedAuthority(userEntity.getRoles().toString())));
		
		this.userEntity = userEntity;
	}
	
	public UserEntity getUserEntity()
	{
		return userEntity;
	}

}
