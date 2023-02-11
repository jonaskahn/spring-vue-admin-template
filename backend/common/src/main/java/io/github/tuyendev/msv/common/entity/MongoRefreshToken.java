package io.github.tuyendev.msv.common.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Document(value = "refresh_tokens")
public class MongoRefreshToken implements Serializable {

    @Id
    private ObjectId id;

    @Indexed
    private ObjectId accessTokenId;

    @Indexed
    private Long userId;

    @Transient
    private String token;

    private Integer status;

    private LocalDateTime expiredAt;
}
