package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    void deleteRefreshTokenByAccessTokenId(final String accessTokenId);
}