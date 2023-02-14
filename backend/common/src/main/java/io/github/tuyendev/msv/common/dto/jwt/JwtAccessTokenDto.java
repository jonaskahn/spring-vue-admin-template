package io.github.tuyendev.msv.common.dto.jwt;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
@AllArgsConstructor
@Builder
public class JwtAccessTokenDto implements Serializable {
	private String id;

	private Date issuedAt;

	private Date expiredAt;

	private String token;

}
