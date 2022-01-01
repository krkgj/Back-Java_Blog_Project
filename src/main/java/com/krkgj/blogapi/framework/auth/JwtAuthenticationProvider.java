package com.krkgj.blogapi.framework.auth;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.Cookie;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider
{
    public final static long ACCESS_TOKEN_VALIDATION_TIME 	= 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_TIME 	= 1000L * 60 * 60 * 24 * 14;
    public final static long SUPER_TOKEN 	= 1000L * 60 * 60 * 24 * 365;
    
    private String SECRET_KEY 			= "inbiznet";
    private String BASE64_ENCODED_KEY 	= Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    
    public String extractUsername(String token)
    {
    	return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token)
    {
    	return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
	    final Claims claims = extractAllClaims(token);
	    return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) throws ExpiredJwtException
    {
        return Jwts.parser().setSigningKey(BASE64_ENCODED_KEY).parseClaimsJws(token).getBody();
    }
    
    public Boolean isTokenExpired(String token)
    {
    	return extractExpiration(token).before(new Date());
    }
     
	public String generateAccessToken(String username) 
	{
	    Map<String, Object> claims = new HashMap<>();
	    return createToken(claims, username, ACCESS_TOKEN_VALIDATION_TIME);
	}
	
	public String generateRefreshToken(String username) 
	{
	    Map<String, Object> claims = new HashMap<>();
	    return createToken(claims, username, REFRESH_TOKEN_VALIDATION_TIME);
	}
	
	private String createToken(Map<String, Object> claims, String subject, long expireTime) 
	{	
	    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
			    .setExpiration(new Date(System.currentTimeMillis() + expireTime))
			    .signWith(SignatureAlgorithm.HS256, BASE64_ENCODED_KEY).compact();
	}
	
	public Long getUserSequence(String token)
	{
		Claims claims =  Jwts.parser().setSigningKey(BASE64_ENCODED_KEY).parseClaimsJws(token).getBody();
		Long value = Long.valueOf(String.valueOf( claims.get("seq")));
		return value;
	}
	
	/**
	 * @설명 토큰의 유효성을 체크한다.
	 * @param token
	 * @param userDetails
	 * @return Boolean
	 */
	public Boolean validateToken(String token) 
	{
	    try 
	    {
			return isTokenExpired(token);
		} 
	    catch (Exception e) 
	    {
			return false;
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
