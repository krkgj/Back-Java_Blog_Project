package com.krkgj.blogapi.api.user.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.krkgj.blogapi.api.user.entity.UserEntity;
import com.krkgj.blogapi.framework.auth.Authority;

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
     * 유저 가입일자
     */
    private LocalDateTime createtime;
    
    /**
     * 유저 권한
     */
    @Enumerated(EnumType.STRING)
    private Authority roles;  
    
	// Builder 패턴
	public static UserDto entity2DtoBuilder(UserEntity userEntity)
	{
		return new UserDtoBuilder()
				.seq(userEntity.getSeq())
				.id(userEntity.getId())
				.password(userEntity.getPassword())
				.roles(userEntity.getRoles())
				.name(userEntity.getName()).build();
	}
}
