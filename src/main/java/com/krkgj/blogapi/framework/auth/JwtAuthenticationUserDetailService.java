package com.krkgj.blogapi.framework.auth;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.krkgj.blogapi.api.token.dto.TokenDto;
import com.krkgj.blogapi.api.token.entity.QTokenEntity;
import com.krkgj.blogapi.api.token.entity.TokenEntity;
import com.krkgj.blogapi.api.token.repository.TokenRepository;
import com.krkgj.blogapi.api.user.bean.UserBean;
import com.krkgj.blogapi.api.user.context.UserContext;
import com.krkgj.blogapi.api.user.dto.UserDto;
import com.krkgj.blogapi.api.user.entity.QUserEntity;
import com.krkgj.blogapi.api.user.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtAuthenticationUserDetailService implements UserDetailsService
{
	private final TokenRepository 	tokenRepository;
	private final PasswordEncoder	passwordEncoder;
	private final JPAQueryFactory 	query;
	
	@Autowired
	private UserBean userBean;

	/**
	 * @설명 유저 정보를 DB에서 UserDetails로 변환 후 리턴.
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException
	{
		QUserEntity user = new QUserEntity("qUser");
		
		UserEntity userResult = query.select(user)
				.from(user)
				.where(user.id.eq(userId))
				.fetchOne();
		
//		if(passwordEncoder.matches(, encodedPassword))
		updateUserBean(userResult);
		

		// 권한 등록?
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(userResult.getRoles()));
		
		UserContext userContext = new UserContext(userResult, authorities);
		
		return userContext;
	}
	
	/**
	 * @설명 유저 정보를 찾는다.
	 * @return UserDetails
	 */
	public boolean findUserInfo(String userId, String password)
	{
		QUserEntity user = new QUserEntity("qUser");
		
		UserEntity userResult = query.select(user)
				.from(user)
				.where(user.id.eq(userId))
				.fetchOne();
		
		if(passwordEncoder.matches(password, userResult.getPassword()))
		{
			return true;
		}
		return false;
	}
	
	
	
	public Boolean findToken(String token)
	{
		QTokenEntity	qToken 		= new QTokenEntity("qToken");
		Long 			count		= 0l;
		
		count = query.selectFrom(qToken)
			.where(qToken.refreshToken.eq(token))
			.fetchCount();
		
		return count > 0l ? true : false;
	}
	
	public String findRefreshToken(String accessToken)
	{
		QTokenEntity qToken = new QTokenEntity("qToken");
		
		String refreshToken = query.select(qToken.refreshToken)
			.from(qToken)
			.where(qToken.accessToken.eq(accessToken))
			.fetchOne();
		
		return refreshToken;
	}
	
	/**
	 * @설명 login_token 테이블에 생성된 토큰 정보 입력
	 * @param tokenDto
	 */
	@Transactional
	public void insertTokens(TokenDto tokenDto)
	{
		TokenEntity tokenEntity = TokenEntity.builder()
				.seqUsers(userBean.getSeq())
				.accessToken(tokenDto.getAccessToken())
				.refreshToken(tokenDto.getRefreshToken())
				.dateCreated(LocalDateTime.now())
				.build();
	
		tokenRepository.save(tokenEntity);
	}
	
	/**
	 * accessToken을 update 한다.
	 * @param accessToken, refreshToken, userSeq
	 */
	@Transactional
	public void updateAccessToken(String accessToken, String refreshToken, long userSeq)
	{
		QTokenEntity qToken = new QTokenEntity("qToken");
		
		query.update(qToken)
			.set(qToken.accessToken, accessToken)
			.where(qToken.seqUsers.eq(userSeq))
			.where(qToken.refreshToken.eq(refreshToken))
			.execute();
	}
	
	private void updateUserBean(UserEntity userEntity)
	{
		userBean.setSeq(userEntity.getSeq());
		userBean.setId(userEntity.getId());
		userBean.setRole(userEntity.getRoles());
		userBean.setName(userEntity.getName());
	}
	
}
