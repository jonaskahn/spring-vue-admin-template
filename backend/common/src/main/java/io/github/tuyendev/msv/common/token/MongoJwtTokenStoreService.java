package io.github.tuyendev.msv.common.token;

import java.util.Date;

import io.github.tuyendev.msv.common.annotation.Executor;
import io.github.tuyendev.msv.common.configurer.ChainedTransactionConfigurer;
import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.entity.MongoAccessToken;
import io.github.tuyendev.msv.common.entity.MongoRefreshToken;
import io.github.tuyendev.msv.common.exception.jwt.RevokedJwtTokenException;
import io.github.tuyendev.msv.common.repository.MongoAccessTokenRepository;
import io.github.tuyendev.msv.common.repository.MongoRefreshTokenRepository;
import io.github.tuyendev.msv.common.security.jwt.JwtAccessToken;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class MongoJwtTokenStoreService implements JwtTokenStore {

	private final MongoAccessTokenRepository accessTokenRepo;

	private final MongoRefreshTokenRepository refreshTokenRepo;

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = ChainedTransactionConfigurer.Mode.MONGO)
	public JwtAccessToken generateTokenInTransaction(Executor<JwtAccessToken> callback) {
		return callback.run();
	}

	@Override
	public void saveAccessToken(String id, Long userId, Date expiration) {
		MongoAccessToken accessToken = MongoAccessToken.builder()
				.id(id)
				.userId(userId)
				.status(EntityStatus.ACTIVE)
				.expiredAt(expiration.toInstant())
				.build();
		accessTokenRepo.save(accessToken);
	}

	@Override
	public void saveRefreshToken(String id, String accessTokenId, Long userId, Date expiration) {
		MongoRefreshToken refreshToken = MongoRefreshToken.builder()
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
		return refreshTokenRepo.findActiveMongoRefreshTokenById(refreshTokenId)
				.map(MongoRefreshToken::getUserId)
				.orElseThrow(RevokedJwtTokenException::new);
	}

	@Override
	public void removeTokensByAccessTokenId(String accessTokenId) {
		accessTokenRepo.deactivateAccessTokenById(accessTokenId);
		refreshTokenRepo.deleteMongoRefreshTokenByAccessTokenId(accessTokenId);
	}

	@Override
	public boolean isAccessTokenExisted(String accessTokenId) {
		return accessTokenRepo.existsActiveMongoAccessTokenById(accessTokenId);
	}

	@Override
	public boolean isRefreshTokenExisted(String refreshTokenId) {
		return refreshTokenRepo.existsById(refreshTokenId);
	}
}
