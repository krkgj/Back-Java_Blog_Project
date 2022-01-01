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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.krkgj.blogapi.api.user.bean.UserBean;
import com.krkgj.blogapi.api.user.dto.UserDto;
import com.krkgj.blogapi.framework.utility.Utility;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{ 

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private final JwtAuthenticationProvider 			jwtProvider; 
	private final JwtAuthenticationUserDetailService 	userDetailsService;
	
	
	@Autowired
	private UserBean userBean;
	
	/**
	 * Filter에서 제외할 경로, 지금은 Login을 요청하는 경로이다.
	 */
	private static final List<String> EXCLUDE_URL = Collections.unmodifiableList(
                    Arrays.asList(
                        "/jwt/token"
                    ));
	
	private void setAuthentication(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) 
	{
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}
	
	/**
	 *  엑세스토큰의 주기를 초단위로 설정하고, 사용자가 액세스토큰으로 요청할 때마다
	 */
	@Override 
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
			throws IOException, ServletException 
	{ 
		logger.info("=============================================================== Security Filter ===============================================================");

		logger.info("client IP : " + request.getRemoteAddr());
		String 			authorizationHeader 		= request.getHeader("Authorization");

		// AccessToken
		String 			token						= null;
		String 			username					= null;
		
		// RefreshToken
		String 			refreshToken				= null;
		String 			refreshUsername				= null;
		
		Long			userSeq						= null;
		try 
		{
			// header에 Bearer + accessToken이 존재하면, accessToken만 추출하여 token 변수에 할당한다.
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
			{
				token = authorizationHeader.substring(7);
				username = jwtProvider.extractUsername(token);
				logger.info("get Token info : " + token);
			}
			else
			{
				logger.info("				Authorization Token is empty.");
			}
			
			if (SecurityContextHolder.getContext().getAuthentication() == null) 
			{
				if(username != null) 
				{
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					
//					userSeq = userBean.getSeq();
					
					if(jwtProvider.validateToken(token))
					{	
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken 
												= jwtProvider.getAuthentication(userDetails);

						usernamePasswordAuthenticationToken
									.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						
						this.setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			}
		} 
		catch (ExpiredJwtException e) 
		{
			logger.error("			AccessToken is Expired : " + e.getMessage());
		
				refreshToken = userDetailsService.findRefreshToken(token);
				
				if(refreshToken == null) logger.error("			Not Found AccessToken in Database ");
		}
		catch (Exception e)
		{
			
		}
        
        try
        {
        	if(Utility.isNotNull(refreshToken))
        	{
    			refreshUsername = jwtProvider.extractUsername(refreshToken);
    			
				UserDetails userDetails = userDetailsService.loadUserByUsername(refreshUsername);

				userSeq = userBean.getSeq();
				
				if(jwtProvider.validateToken(refreshToken))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken 
											= jwtProvider.getAuthentication(userDetails);

					usernamePasswordAuthenticationToken
								.setDetails( new WebAuthenticationDetailsSource().buildDetails(request) );
					
					this.setAuthentication(usernamePasswordAuthenticationToken);
					
					token = jwtProvider.generateAccessToken(refreshUsername);
					
					
					userDetailsService.updateAccessToken(token, refreshToken, userSeq);
					
				    response.addCookie( jwtProvider.generateCookie(token) );
				}
        	}
        }
        catch (ExpiredJwtException e)
        {
			logger.error("			RefreshToken is Expired : " + e.getMessage());
        }
		filterChain.doFilter(request, response);
		logger.info("===============================================================================================================================================");
	} 

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
	{
		return EXCLUDE_URL.stream().anyMatch(url -> request.getServletPath().startsWith(url));
	}
	
	

	
	
//	private String getToken(HttpServletRequest request)
//	{ 
//		String headerAuth = request.getHeader("Authorization"); 
//		if (headerAuth != null && headerAuth.startsWith("Bearer ")) 
//		{ 
//			return headerAuth.substring(7, headerAuth.length()); 
//		} 
//		return null;
//	} 
}
