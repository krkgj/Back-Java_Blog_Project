package com.krkgj.blogapi.framework.auth;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krkgj.blogapi.api.token.dto.TokenDto;
import com.krkgj.blogapi.api.user.bean.UserBean;
import com.krkgj.blogapi.api.user.dto.UserDto;
import com.krkgj.blogapi.framework.result.ResultCode;
import com.krkgj.blogapi.framework.result.ResultMap;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class JwtAuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

	private final AuthenticationManager authenticationManager;

	private final JwtAuthenticationProvider 			jwtProvider;
	private final JwtAuthenticationUserDetailService 	jwtAuthenticationUserDetailService;
	
	private final PasswordEncoder	passwordEncoder;
	
	@Autowired
	private UserBean userBean;

	/**
	 * @설명 최초 로그인 시에 1번만 동작한다.
	 * @param authDto : 유저 아이디와 유저 패스워드가 포함된 객체
	 * @return 오류코드 / 토큰 / 유저이름
	 */
	@PostMapping(value = { "/token" })
	private ResultMap generateTokens(@RequestBody UserDto userDto, HttpServletResponse res) 
	{
		ResultMap 	resultMap 	= new ResultMap();
		String 		userId 		= userDto.getId();
		String 		password 	= userDto.getPassword();
		
		try 
		{
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, password);

			authenticationManager.authenticate(token);

			logger.info("				Authentication OK!");
			
			String accessToken 	= jwtProvider.generateAccessToken(userId);
			String refreshToken = jwtProvider.generateRefreshToken(userId);

			resultMap.put("code", 			ResultCode.RESULT_OK);
			resultMap.put("accessToken",	accessToken);
			resultMap.put("userName", 		userBean.getName());

			TokenDto tokenDto = TokenDto.builder()
					.accessToken(accessToken)
					.refreshToken(refreshToken)
					.build();	
			
			// accessToken과 refreshToken 모두 DB에 저장한다. => accessToken에 따른 사용자를 찾고, 
			// 후에 그 사용자의 refreshToken을 갖고오기 위해서,
			jwtAuthenticationUserDetailService.insertTokens(tokenDto);
			
			return resultMap;
		} 
		catch (Exception ex) 
		{
			// EXCEPTION
			// send 404
			logger.error("				NOT FOUND USER : " + ex.getMessage());
			resultMap.put("code", ResultCode.USER_NOT_FOUND);
			resultMap.put("message", "USER NOT FOUND");
			return resultMap;
		}
	}
}
