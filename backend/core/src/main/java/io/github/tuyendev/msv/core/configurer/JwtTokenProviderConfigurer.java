package io.github.tuyendev.msv.core.configurer;

import io.github.tuyendev.msv.common.repository.MongoAccessTokenRepository;
import io.github.tuyendev.msv.common.repository.MongoRefreshTokenRepository;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenStore;
import io.github.tuyendev.msv.common.token.MongoJwtTokenStoreService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenProviderConfigurer {

	@Bean
	JwtTokenStore jwtTokenStore(MongoAccessTokenRepository accessTokenRepo, MongoRefreshTokenRepository refreshTokenRepo) {
		return new MongoJwtTokenStoreService(accessTokenRepo, refreshTokenRepo);
	}
}
