package com.krkgj.blogapi.api.user.context;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.krkgj.blogapi.api.user.entity.UserEntity;


public class UserContext extends User
{
	private static final long serialVersionUID = 1L;
	
	private final UserEntity userEntity;

	public UserContext(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) 
	{
		super(userEntity.getId(), userEntity.getPassword(), authorities);
		
		this.userEntity = userEntity;
	}
	
	public UserEntity getUserEntity()
	{
		return userEntity;
	}

}
