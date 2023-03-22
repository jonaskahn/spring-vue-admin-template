package io.github.tuyendev.msv.common.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@Document(value = "access_tokens")
public class MongoAccessToken implements Serializable {

    @Id
    private String id;

    @Indexed
    private Long userId;

    private Instant expiredAt;

    @DocumentReference(lazy = true)
    private MongoRefreshToken refreshToken;
}
