package com.krkgj.blogapi.api.user.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.krkgj.blogapi.api.user.dto.UserDto;
import com.krkgj.blogapi.framework.auth.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
@Builder
@Nullable
public class UserEntity implements Serializable
{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    /**
     * 유저 아이디
     */
    @Column(name = "id")
    private String id;
    
    /**
     * 유저 이름
     */
    @Column(name = "name")
    private String name;

    /**
     * 유저 패스워드
     */
    @Column(name = "password")
    private String password;
    
    /**
     * 유저 가입일자
     */
    @Column(name = "createtime")
    @CreatedDate
    private LocalDateTime createtime;

    /**
     * 유저 권한
     */
    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Authority roles;
    
	// Builder 패턴
	public static UserEntity dto2EntityBuilder(UserDto userDto, PasswordEncoder passwordEncoder)
	{
		return new UserEntityBuilder()
				.seq(userDto.getSeq())
				.id(userDto.getId())
				.password(passwordEncoder.encode(userDto.getPassword()))
				.roles(userDto.getRoles())
				.createtime(LocalDateTime.now())
				.name(userDto.getName()).build();
	}
}
