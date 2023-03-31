package io.github.tuyendev.msv.common.constant;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.github.tuyendev.msv.common.utils.Translator.eval;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Authorization {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	public static final String BEARER_TOKEN_PREFIX = "Bearer ";

	public static final String BEARER_TOKEN_VALUE = "BearerTokenValue";


	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public enum TokenType {

		ACCESS("ACCESS", "app.common.token.access.name"),

		REFRESH("REFRESH", "app.common.token.refresh.name");

		private static final Map<String, TokenType> data = Map.of(
				ACCESS.name, ACCESS,
				REFRESH.name, REFRESH
		);

		String name;

		String desc;

		TokenType(String name, String desc) {
			this.name = name;
			this.desc = desc;
		}

		public static TokenType typeOf(String type) {
			return data.get(type);
		}

		@JsonValue
		public String getName() {
			return name;
		}

		public String getDesc() {
			return eval(this.desc);
		}
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class JwtClaim {

		public static final String AUTHORITY = "x-authority";

		public static final String REMEMBER_ME = "rem";

		public static final String TYPE = "x-type";

		public static final String EMAIL = "email";

		public static final String PREFERRED_USERNAME = "preferred_username";

		public static final String FAMILY_NAME = "family_name";

		public static final String GIVEN_NAME = "given_name";

		public static final String NAME = "name";
	}

}
