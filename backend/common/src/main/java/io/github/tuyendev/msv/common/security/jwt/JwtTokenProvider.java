package io.github.tuyendev.msv.common.security.jwt;


import io.github.tuyendev.msv.common.dto.token.TokenInfoDto;

public interface JwtTokenProvider {

	JwtAccessToken generateToken(final String username, final String password, final boolean rememberMe);

	JwtAccessToken renewToken(final String jwtToken);

	void authorizeToken(final String jwtToken);

	/**
	 * Privately use to distinguish oauth2 token between self-issued jwt token
	 *
	 * @param jwtToken
	 * @return
	 */
	boolean isSelfIssuer(final String jwtToken);

	TokenInfoDto getTokenInfo(final String jwtToken);
}
