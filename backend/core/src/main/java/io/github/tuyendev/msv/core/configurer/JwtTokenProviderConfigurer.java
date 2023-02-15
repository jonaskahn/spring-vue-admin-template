package io.github.tuyendev.msv.core.configurer;

import io.github.tuyendev.msv.common.repository.AccessTokenRepository;
import io.github.tuyendev.msv.common.repository.RefreshTokenRepository;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenStore;
import io.github.tuyendev.msv.common.token.JpaJwtTokenStoreService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenProviderConfigurer {

	@Bean
	JwtTokenStore jwtTokenStore(AccessTokenRepository accessTokenRepo, RefreshTokenRepository refreshTokenRepo) {
		return new JpaJwtTokenStoreService(accessTokenRepo, refreshTokenRepo);
	}
}
