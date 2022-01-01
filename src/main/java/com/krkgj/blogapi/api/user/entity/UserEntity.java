package com.krkgj.blogapi.api.user.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
     * 유저 권한
     */
    @Column(name = "roles")
    private String roles;
    

}
