package com.krkgj.blogapi.api.token.entity;

import java.time.LocalDateTime;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krkgj.blogapi.api.token.dto.TokenDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "token_info")
@Builder
@Nullable
public class RefreshTokenEntity 
{
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    /**
     * 리프레쉬 토큰 값
     */
    @Column(name = "refresh_token")
    private String refreshToken;
  
    /**
     * 토큰 등록 시간
     */
    @Column(name = "createtime")
    private LocalDateTime createtime;
    
	// Builder 패턴
	public static RefreshTokenEntity tokenDto2RefreshEntityBuilder(TokenDto tokenDto)
	{
		return new RefreshTokenEntityBuilder()
				.seq(tokenDto.getSeq())
				.refreshToken(tokenDto.getRefreshToken())
				.createtime(tokenDto.getCreatetime())
				.build();
	}
}
