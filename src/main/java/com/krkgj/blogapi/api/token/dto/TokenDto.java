package com.krkgj.blogapi.api.token.dto;

import java.time.LocalDateTime;

import com.krkgj.blogapi.api.token.entity.TokenEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenDto 
{
    private Long seq;
    
    /**
     * 액세스 토큰 값
     */
    private String accessToken;
    
    /**
     * 리프레쉬 토큰 값
     */
    private String refreshToken;
    
    /**
     * 토큰 등록 시간
     */
    private LocalDateTime createtime;
    
	// Builder 패턴
	public static TokenDto entity2DtoBuilder(TokenEntity tokenEntity)
	{
		return new TokenDtoBuilder()
				.seq(tokenEntity.getSeq())
				.refreshToken(tokenEntity.getRefreshToken())
				.createtime(tokenEntity.getCreatetime())
				.build();
	}

}
