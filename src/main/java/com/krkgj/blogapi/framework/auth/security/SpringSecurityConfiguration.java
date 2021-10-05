//package com.krkgj.blogapi.framework.auth.security;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter
//{
//	@Override
//	protected void configure(HttpSecurity http) throws Exception 
//	{
//		// TODO Auto-generated method stub
//		http.
//		csrf().disable().			// csrf 사용 안 함 => REST API 이므로,
//		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	// JWT인증사용하므로 세션 사용  함
//		.and()
//			.authorizeRequests() // 다음 리퀘스트에 대한 사용 권한을 체크
//			.antMatchers("signIn", "signUp").permitAll()
//			.anyRequest().hasRole("USER")
//		.and()
//			.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint())
//		.and()
//			.addFilterBefore(filter, beforeFilter)
//		
//		
//	}
//
//}
