package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.MongoAccessToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAccessTokenRepository extends MongoRepository<MongoAccessToken, ObjectId> {
}
