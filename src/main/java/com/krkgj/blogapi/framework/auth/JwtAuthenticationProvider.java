package com.krkgj.blogapi.framework.auth;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.krkgj.blogapi.api.token.dto.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider
{
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
	
    public final static long ACCESS_TOKEN_VALIDATION_TIME 	= 1000L * 60 * 2;
    public final static long REFRESH_TOKEN_VALIDATION_TIME 	= 1000L * 60 * 60 * 24 * 14;
    
    private final JwtAuthenticationUserDetailService jwtAuthService;
    
    private String SECRET_KEY 			= "secret_key_krkgj_blog_development_krkgj_i_love_development";
    private String BASE64_ENCODED_KEY 	= Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    
    
    /**
     * @설명 
     * @param authentication
     * @return
     */
    @SuppressWarnings("deprecation")
	public String generateAccessToken(Authentication authentication)
    {
        // 권한들 가져오기
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
//                .claim("roles", authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATION_TIME)) // payload "exp": 1516239022 (예시)
                .signWith(SignatureAlgorithm.HS256, BASE64_ENCODED_KEY)    // header "alg": "HS512"
                .compact();

        return accessToken;
    }
    
    /**
     * @설명 
     * @param authentication
     * @return
     */
    @SuppressWarnings("deprecation")
	public String generateRefreshToken(Authentication authentication)
    {
        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDATION_TIME))
                .signWith(SignatureAlgorithm.HS256, BASE64_ENCODED_KEY)
                .compact();
        
        return refreshToken;
    }

    public Authentication getAuthentication(String accessToken) 
    {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("roles") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) 
    {
        try 
        {
        	final Claims claims  = parseClaims(token);

        	System.out.println(claims.getSubject());
        	System.out.println(claims.getExpiration());
        	
        	final String getUsernameOfUserDetail = jwtAuthService.loadUserByUsername(claims.getSubject()).getUsername();
        	
        	Boolean isTokenExpired = claims.getExpiration().before(new Date());
        	
        	return getUsernameOfUserDetail.equals(claims.getSubject()) && !isTokenExpired;
        } 
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) 
        {
            logger.info("잘못된 JWT 서명입니다.");
        } 
        catch (ExpiredJwtException e) 
        {
            logger.info("만료된 JWT 토큰입니다.");
        } 
        catch (UnsupportedJwtException e) 
        {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } 
        catch (IllegalArgumentException e) 
        {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) 
    {
        try {
            return Jwts.parserBuilder().setSigningKey(BASE64_ENCODED_KEY).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

	 
	public UsernamePasswordAuthenticationToken getAuthentication(UserDetails userDetails) 
	{
	    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	/**
	 * @설명 token을 Cookie에 담아 리턴
	 * @param token
	 * @return cookie
	 */
	public Cookie generateCookie(String token)
	{
		Cookie cookie = new Cookie("accessToken", token);
	    cookie.setMaxAge(60 * 60 * 24 * 2);
		cookie.setPath("/");
		
		return cookie;
	}

}
