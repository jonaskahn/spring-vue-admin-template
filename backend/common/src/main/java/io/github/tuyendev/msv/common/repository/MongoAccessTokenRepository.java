package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.entity.MongoAccessToken;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAccessTokenRepository extends MongoRepository<MongoAccessToken, String> {

	boolean existsMongoAccessTokenByIdAndStatus(String id, Integer status);

	default boolean existsActiveMongoAccessTokenById(String id) {
		return existsMongoAccessTokenByIdAndStatus(id, EntityStatus.ACTIVE);
	}

	default void deactivateAccessTokenById(String id) {
		this.findById(id).ifPresent(mongoAccessToken -> {
			mongoAccessToken.setStatus(EntityStatus.INACTIVE);
			save(mongoAccessToken);
		});
	}
}
