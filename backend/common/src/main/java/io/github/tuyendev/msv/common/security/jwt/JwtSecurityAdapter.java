package io.github.tuyendev.msv.common.security.jwt;

import io.github.tuyendev.msv.common.security.DefaultAuthenticationFilter;
import io.github.tuyendev.msv.common.security.RestAuthenticationEntryPoint;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final JwtTokenProvider jwtTokenProvider;

	private final RestAuthenticationEntryPoint authenticationEntryPoint;

	public JwtSecurityAdapter(JwtTokenProvider jwtTokenProvider, RestAuthenticationEntryPoint authenticationEntryPoint) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	public void configure(HttpSecurity http) {
		http.addFilterBefore(new DefaultAuthenticationFilter(jwtTokenProvider, authenticationEntryPoint), UsernamePasswordAuthenticationFilter.class);
	}
}
