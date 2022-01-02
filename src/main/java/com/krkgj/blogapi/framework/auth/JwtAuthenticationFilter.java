package com.krkgj.blogapi.framework.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.krkgj.blogapi.api.user.bean.UserBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public static final String AUTHORIZATION_HEADER 	= "Authorization";
    public static final String BEARER_PREFIX 			= "Bearer ";
    
	private final JwtAuthenticationProvider 			jwtProvider; 
	
	/**
	 * Filter에서 제외할 경로, 지금은 Login을 요청하는 경로이다.
	 */
	private static final List<String> EXCLUDE_URL = Collections.unmodifiableList(
                    Arrays.asList(
                        "/user/auth/**"
                    ));
	
	/**
	 *  엑세스토큰의 주기를 초단위로 설정하고, 사용자가 액세스토큰으로 요청할 때마다
	 */
	@Override 
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
			throws IOException, ServletException 
	{ 
		logger.info("=============================================================== Security Filter ===============================================================");
		logger.info("client IP : " + request.getRemoteAddr());
		
        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = resolveToken(request);

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) 
        {
            Authentication authentication = jwtProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
		logger.info("===============================================================================================================================================");
	} 

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
	{
		return EXCLUDE_URL.stream().anyMatch(url -> request.getServletPath().startsWith(url));
	}
	
    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
