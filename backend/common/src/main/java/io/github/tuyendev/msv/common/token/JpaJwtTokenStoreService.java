package io.github.tuyendev.msv.common.token;

import java.util.Date;

import io.github.tuyendev.msv.common.annotation.Executor;
import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.entity.AccessToken;
import io.github.tuyendev.msv.common.entity.RefreshToken;
import io.github.tuyendev.msv.common.exception.jwt.RevokedJwtTokenException;
import io.github.tuyendev.msv.common.repository.AccessTokenRepository;
import io.github.tuyendev.msv.common.repository.RefreshTokenRepository;
import io.github.tuyendev.msv.common.security.jwt.JwtAccessToken;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenStore;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class JpaJwtTokenStoreService implements JwtTokenStore {

	private final AccessTokenRepository accessTokenRepo;

	private final RefreshTokenRepository refreshTokenRepo;

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public JwtAccessToken generateTokenInTransaction(Executor<JwtAccessToken> callback) {
		return callback.run();
	}

	@Override
	public void saveAccessToken(String id, Long userId, @NonNull Date expiration) {
		AccessToken accessToken = AccessToken.builder()
				.id(id)
				.userId(userId)
				.expiredAt(expiration.toInstant())
				.status(EntityStatus.ACTIVE)
				.build();
		accessTokenRepo.save(accessToken);
	}

	@Override
	public void saveRefreshToken(String id, String accessTokenId, Long userId, @NonNull Date expiration) {
		RefreshToken refreshToken = RefreshToken.builder()
				.id(id)
				.accessTokenId(accessTokenId)
				.userId(userId)
				.expiredAt(expiration.toInstant())
				.status(EntityStatus.ACTIVE)
				.build();
		refreshTokenRepo.save(refreshToken);
	}

	@Override
	public Long getUserIdByRefreshTokenId(String refreshTokenId) {
		return refreshTokenRepo.findActiveRefreshTokenBy(refreshTokenId)
				.map(RefreshToken::getUserId)
				.orElseThrow(RevokedJwtTokenException::new);
	}

	@Override
	public void removeTokensByAccessTokenId(String accessTokenId) {
		accessTokenRepo.deleteById(accessTokenId);
		refreshTokenRepo.deleteRefreshTokenByAccessTokenId(accessTokenId);
	}

	@Override
	public boolean isAccessTokenExisted(String accessTokenId) {
		return accessTokenRepo.existsActiveAccessTokenById(accessTokenId);
	}

	@Override
	public boolean isRefreshTokenExisted(String refreshTokenId) {
		return refreshTokenRepo.existsById(refreshTokenId);
	}
}
