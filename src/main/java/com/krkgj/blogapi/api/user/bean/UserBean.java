package com.krkgj.blogapi.api.user.bean;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @FileName : UserBean.java
 * @작성자   : 김경진
 * @설명 : API 요청 시마다 유저 정보를 가지고 있는 UserBean
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class UserBean 
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
     * 유저 권한
     */
    private String role;
    
}
