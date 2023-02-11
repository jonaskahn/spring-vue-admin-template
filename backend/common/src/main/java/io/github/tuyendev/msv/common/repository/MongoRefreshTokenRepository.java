package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.MongoRefreshToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRefreshTokenRepository extends MongoRepository<MongoRefreshToken, ObjectId> {
}
