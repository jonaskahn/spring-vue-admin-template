package io.github.tuyendev.msv.common.repository;

import java.util.Optional;

import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.entity.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

	Optional<RefreshToken> findRefreshTokenByIdAndStatus(final String id, final Integer status);

	default Optional<RefreshToken> findActiveRefreshTokenBy(final String id) {
		return findRefreshTokenByIdAndStatus(id, EntityStatus.ACTIVE);
	}

	void deleteRefreshTokenByAccessTokenId(final String accessTokenId);
}