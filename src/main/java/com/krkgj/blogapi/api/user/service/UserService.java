package com.krkgj.blogapi.api.user.service;



import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krkgj.blogapi.api.token.dto.TokenDto;
import com.krkgj.blogapi.api.token.entity.RefreshTokenEntity;
import com.krkgj.blogapi.api.token.repository.RefreshTokenRepository;
import com.krkgj.blogapi.api.user.dto.UserDto;
import com.krkgj.blogapi.api.user.entity.UserEntity;
import com.krkgj.blogapi.api.user.repository.UserRepository;
import com.krkgj.blogapi.framework.auth.JwtAuthenticationProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService 
{
	private final JPAQueryFactory query;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	
	private final AuthenticationManager authenticationManager;

	private final JwtAuthenticationProvider jwtProvider;
	
	private final PasswordEncoder passwordEncoder;
	
	
	public UserDto findUserInfo(long userSeq)
	{
		return UserDto.entity2DtoBuilder(userRepository.findById(userSeq).get());
	}
	
	public UserDto signUp(UserDto userDto)
	{
		UserEntity userEntity = UserEntity.dto2EntityBuilder(userDto, passwordEncoder);
		
		return UserDto.entity2DtoBuilder(userRepository.save(userEntity));
	}
	
	public TokenDto login(UserDto userDto)
	{
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getId(), userDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 JwtAuthenticationUserDetailService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        
        TokenDto tokenDto = getTokenDto(accessToken, refreshToken);

        refreshTokenRepository.save(RefreshTokenEntity.tokenDto2RefreshEntityBuilder(tokenDto));
        
        return tokenDto;
	}
	
	private TokenDto getTokenDto(String accessToken, String refreshToken)
	{
		return new TokenDto(null, accessToken, refreshToken, LocalDateTime.now());
	}
}
