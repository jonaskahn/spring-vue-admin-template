package io.github.tuyendev.msv.common.token;

import io.github.tuyendev.msv.common.security.jwt.JwtTokenStore;

import java.util.Date;

public class MongoJwtTokenStoreService implements JwtTokenStore {
    @Override
    public void saveAccessToken(String id, Long userId, Date expiration) {

    }

    @Override
    public void saveRefreshToken(String id, String accessTokenId, Long userId, Date expiration) {

    }

    @Override
    public Long getUserIdByRefreshTokenId(String refreshTokenId) {
        return null;
    }

    @Override
    public void inactiveAccessTokenById(String id) {

    }

    @Override
    public void inactiveRefreshTokenById(String id) {

    }

    @Override
    public boolean isAccessTokenExisted(String accessTokenId) {
        return false;
    }
}
