package io.github.tuyendev.msv.common.security.jwt;

import java.util.Date;

import io.github.tuyendev.msv.common.annotation.Executor;

public interface JwtTokenStore {

	/**
	 * This method used as an indicator to generate token in transaction (for internal use)
	 * @param callback function execute real call
	 * @return {@link JwtAccessToken}
	 */
	JwtAccessToken generateToken(Executor<JwtAccessToken> callback);

	void saveAccessToken(String id, Long userId, Date expiration);

	void saveRefreshToken(String id, String accessTokenId, Long userId, Date expiration);

	Long getUserIdByRefreshTokenId(String refreshTokenId);

	void inactiveAccessTokenById(String id);

	void inactiveRefreshTokenById(String id);

	boolean isAccessTokenExisted(String accessTokenId);

	boolean isRefreshTokenExisted(String refreshTokenId);
}
