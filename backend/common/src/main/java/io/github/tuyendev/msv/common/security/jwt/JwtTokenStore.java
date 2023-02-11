package io.github.tuyendev.msv.common.security.jwt;

import java.util.Date;

public interface JwtTokenStore {
    void saveAccessToken(String id, Long userId, Date expiration);

    void saveRefreshToken(String id, String accessTokenId, Long userId, Date expiration);

    Long getUserIdByRefreshTokenId(String refreshTokenId);

    void inactiveAccessTokenById(String id);

    void inactiveRefreshTokenById(String id);

    boolean isAccessTokenExisted(String accessTokenId);
}
