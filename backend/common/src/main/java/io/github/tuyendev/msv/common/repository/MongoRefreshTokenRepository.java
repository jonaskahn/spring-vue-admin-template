package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.MongoRefreshToken;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRefreshTokenRepository extends MongoRepository<MongoRefreshToken, String> {

	void deleteMongoRefreshTokenByAccessTokenId(final String accessTokenId);
}
