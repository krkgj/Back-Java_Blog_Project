package com.krkgj.blogapi.api.token.entity;

import java.time.LocalDateTime;

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
@Table(name = "users_token")
@Builder
@Nullable
public class TokenEntity 
{
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    
    @Column(name = "seq_users")
    private Long seqUsers;
    
    /**
     * 엑세스 토큰 값
     */
    @Column(name = "access_token")
    private String accessToken;
    
    /**
     * 리프레쉬 토큰 값
     */
    @Column(name = "refresh_token")
    private String refreshToken;
  
    /**
     * 토큰 등록 시간
     */
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
}
