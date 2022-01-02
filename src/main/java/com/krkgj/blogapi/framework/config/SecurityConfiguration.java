package com.krkgj.blogapi.framework.config;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.krkgj.blogapi.framework.auth.JwtAccessDeniedHandler;
import com.krkgj.blogapi.framework.auth.JwtAuthenticationEntryPoint;
import com.krkgj.blogapi.framework.auth.JwtAuthenticationFilter;
import com.krkgj.blogapi.framework.auth.JwtAuthenticationUserDetailService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	private final JwtAuthenticationUserDetailService userDetailService;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationFilter jwtFilter;
	
	
	/**
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(userDetailService);
	}
	
	/**
	 * 스프링 시큐리티 기본 설정, /login을 제외한 모든 경로에 jwfFillter를 설정.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers("/user/auth/**").permitAll()
		.anyRequest().authenticated()
		
		.and().exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler)
        
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		.and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
        web.ignoring().antMatchers("/api/docs/**");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception 
	{
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
    /**
     * cors를 모두 허용
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() 
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
