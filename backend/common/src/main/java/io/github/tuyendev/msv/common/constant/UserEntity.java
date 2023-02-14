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
}
