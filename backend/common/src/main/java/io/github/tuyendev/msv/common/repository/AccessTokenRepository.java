package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.entity.AccessToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

	boolean existsAccessTokenByIdAndStatus(String id, Integer status);

	default boolean existsActiveAccessTokenById(String id) {
		return existsAccessTokenByIdAndStatus(id, EntityStatus.ACTIVE);
	}
}