package com.krkgj.blogapi.framework.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.krkgj.blogapi.api.user.bean.UserBean;
import com.krkgj.blogapi.api.user.context.UserContext;
import com.krkgj.blogapi.api.user.entity.UserEntity;
import com.krkgj.blogapi.api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtAuthenticationUserDetailService implements UserDetailsService
{
	private final UserRepository userRepository;
	
	@Autowired
	private UserBean userBean;

	/**
	 * @설명 유저 정보를 DB에서 UserDetails로 변환 후 리턴.
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException
	{
		// Optional로 싸인 userEntity를 추출, 새로운 User 객체 생성 후 리턴한다.

		System.out.println("======================================a");
		System.out.println(userId);
		Optional<UserEntity> oUserEntity = userRepository.findById(userId);
		
		updateUserBean(oUserEntity.get());
		
		return oUserEntity
			.map(userEntity -> new UserContext(userEntity))
			.orElseThrow(() -> new UsernameNotFoundException(userId + "를 찾을 수 없습니다."));
	}
	
	private void updateUserBean(UserEntity userEntity)
	{
		userBean.setSeq(userEntity.getSeq());
		userBean.setId(userEntity.getId());
		userBean.setRole(userEntity.getRoles());
		userBean.setName(userEntity.getName());
	}
	
}
