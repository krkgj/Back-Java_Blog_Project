package com.krkgj.blogapi.api.token.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto 
{
    private Long seq;
    
    private Long seqUsers;
    
    /**
     * 엑세스 토큰 값
     */
    private String accessToken;
    
    /**
     * 리프레쉬 토큰 값
     */
    private String refreshToken;
    
    /**
     * 마지막 요청 시간
     */
    private LocalDateTime lastRequest;
    
    private char enabled;
    
    /**
     * 토큰 등록 시간
     */
    private LocalDateTime dateCreated;

}
