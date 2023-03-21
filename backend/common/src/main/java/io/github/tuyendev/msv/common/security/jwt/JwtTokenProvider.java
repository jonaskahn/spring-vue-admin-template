package io.github.tuyendev.msv.common.security.jwt;


import io.github.tuyendev.msv.common.dto.token.TokenInfoDto;

public interface JwtTokenProvider {

	JwtAccessToken generateToken(final String username, final String password, final boolean rememberMe);

	JwtAccessToken renewToken(final String token);

	void authorizeToken(final String token);

	/**
	 * Privately use to distinguish between oauth2 token and self-issued jwt token
	 *
	 * @param token
	 * @return
	 */
	boolean isSelfIssuer(final String token);

	void revokeToken(final String token);

	TokenInfoDto getTokenInfo(final String token);
}
