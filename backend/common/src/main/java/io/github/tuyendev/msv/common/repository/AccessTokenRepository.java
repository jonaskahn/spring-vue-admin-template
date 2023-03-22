package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

    boolean existsAccessTokenById(String id);

}