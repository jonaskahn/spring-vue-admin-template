package io.github.tuyendev.msv.common.security.jwt;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtAccessToken implements Serializable {

	private Type type;

	private String accessToken;

	private Long accessTokenExpiredAt;

	private String refreshToken;

	private Long refreshTokenExpiredAt;

	public JwtAccessToken() {
		this.type = Type.BEARER;
	}

	@Builder
	public JwtAccessToken(Type type, String accessToken, Long accessTokenExpiredAt,
			String refreshToken, Long refreshTokenExpiredAt) {
		this.type = Objects.isNull(type) ? Type.BEARER : type;
		this.accessToken = accessToken;
		this.accessTokenExpiredAt = accessTokenExpiredAt;
		this.refreshToken = refreshToken;
		this.refreshTokenExpiredAt = refreshTokenExpiredAt;
	}

	public enum Type {
		BEARER("bearer");

		final String value;

		Type(String value) {
			this.value = value;
		}

		@JsonValue
		public String getValue() {
			return value;
		}
	}

}
