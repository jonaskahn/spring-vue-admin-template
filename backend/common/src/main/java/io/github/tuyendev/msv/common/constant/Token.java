package io.github.tuyendev.msv.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Token {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Audience {
		public static final String ACCESS_TOKEN = "ACC";

		public static final String REFRESH_TOKEN = "REF";

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Claim {
		public static final String AUTHORITY = "auth";

		public static final String REMEMBER_ME = "rem";

		public static final String EMAIL = "email";

		public static final String PREFERRED_USERNAME = "preferred_username";

		public static final String FAMILY_NAME = "family_name";

		public static final String GIVEN_NAME = "given_name";

		public static final String NAME = "name";
	}

}
