package io.github.tuyendev.msv.common.entity;

import java.io.Serializable;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(value = "refresh_tokens")
public class MongoRefreshToken implements Serializable {

	@Id
	private String id;

	@Indexed
	private String accessTokenId;

	@Indexed
	private Long userId;

	private Instant expiredAt;
}
