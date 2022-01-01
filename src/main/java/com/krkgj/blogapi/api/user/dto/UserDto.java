package com.krkgj.blogapi.api.user.dto;

import java.math.BigDecimal;

import com.krkgj.blogapi.api.user.entity.UserEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto 
{
    /**
     * 유저 index 값 = 시퀀스 값
     */
    private Long seq;

    /**
     * 유저 이름
     */
    private String name;  
    
    /**
     * 유저 아이디
     */
    private String id;
    
    /**
     * 유저 패스워드
     */
    private String password;

    /**
     * 유저 휴가
     */
    private BigDecimal vacation;  
    
    /**
     * 유저 권한
     */
    private String roles;  
    
    
    
    
	// Builder 패턴
	public static UserDto builder(UserEntity userEntity)
	{
		return new UserDtoBuilder()
				.seq(userEntity.getSeq())
				.id(userEntity.getId())
				.roles(userEntity.getRoles())
				.name(userEntity.getName()).build();
	}
}
