package io.github.tuyendev.msv.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserEntity {

	public static final Long DEFAULT_USER_ADMIN_ID = 1L;

	public static final Long DEFAULT_USER_ANONYMOUS_ID = 0L;

	public static final Integer EMAIL_VERIFIED = 1;

	public static final Integer EMAIL_NOT_VERIFIED = 0;

	public static final Integer PHONE_VERIFIED = 1;

	public static final Integer PHONE_NOT_VERIFIED = 0;

	public enum Gender {
		MEN(1),
		WOMEN(2),
		UNKNOWN(3);

		int value;

		Gender(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}

	public enum EmailVerify {
		VERIFIED(1),
		NOT_VERIFIED(0);

		int value;

		EmailVerify(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}

	public enum PhoneVerify {
		VERIFIED(1),
		NOT_VERIFIED(0);

		int value;

		PhoneVerify(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}
}
