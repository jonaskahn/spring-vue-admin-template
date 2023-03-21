package io.github.tuyendev.msv.common.repository;

import java.util.Optional;

import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.entity.MongoRefreshToken;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRefreshTokenRepository extends MongoRepository<MongoRefreshToken, String> {

	Optional<MongoRefreshToken> findByIdAndStatus(String id, Integer status);

	default Optional<MongoRefreshToken> findActiveMongoRefreshTokenById(String id) {
		return findByIdAndStatus(id, EntityStatus.ACTIVE);
	}

	void deleteMongoRefreshTokenByAccessTokenId(final String accessTokenId);
}
