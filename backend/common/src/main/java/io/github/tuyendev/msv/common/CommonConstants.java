package io.github.tuyendev.msv.common;

public final class CommonConstants {

    public static final class EntityStatus {
        public static final Integer INACTIVE = 0;

        public static final Integer ACTIVE = 1;

        public static final Integer LOCKED = 2;

        public static final Integer UNLOCKED = ACTIVE;
    }

    public static final class User {
        public static final Long DEFAULT_USER_ADMIN_ID = 1L;

        public static final Long DEFAULT_USER_ANONYMOUS_ID = 0L;
    }
}
