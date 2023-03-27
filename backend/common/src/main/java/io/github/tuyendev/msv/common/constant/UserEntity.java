package io.github.tuyendev.msv.common.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserEntity {

    public static final Long DEFAULT_USER_ADMIN_ID = 1L;

    public static final Long DEFAULT_USER_EDITOR_ID = 2L;

    public static final Long DEFAULT_USER_ANONYMOUS_ID = 0L;

    public static final Integer EMAIL_VERIFIED = 1;

    public static final Integer EMAIL_NOT_VERIFIED = 0;

    public static final Integer PHONE_VERIFIED = 1;

    public static final Integer PHONE_NOT_VERIFIED = 0;

    public enum Gender {
        MEN(1),
        WOMEN(2),
        UNKNOWN(3);

        private static final Map<Integer, Gender> data = Map.of(
                MEN.value, MEN,
                WOMEN.value, WOMEN,
                UNKNOWN.value, UNKNOWN
        );

        final int value;

        Gender(int value) {
            this.value = value;
        }

        @JsonCreator
        public static Gender from(Integer value) {
            return data.get(value);
        }

        @JsonValue
        public int value() {
            return value;
        }
    }

    public enum EmailVerify {
        VERIFIED(1),
        NOT_VERIFIED(0);

        private static final Map<Integer, EmailVerify> data = Map.of(
                VERIFIED.value, VERIFIED,
                NOT_VERIFIED.value, NOT_VERIFIED
        );

        final int value;

        EmailVerify(int value) {
            this.value = value;
        }

        @JsonCreator
        public static EmailVerify from(Integer value) {
            return data.get(value);
        }

        @JsonValue
        public int value() {
            return value;
        }
    }

    public enum PhoneVerify {
        VERIFIED(1),
        NOT_VERIFIED(0);

        private static final Map<Integer, PhoneVerify> data = Map.of(
                VERIFIED.value, VERIFIED,
                NOT_VERIFIED.value, NOT_VERIFIED
        );

        final int value;

        PhoneVerify(int value) {
            this.value = value;
        }

        @JsonCreator
        public static PhoneVerify from(Integer value) {
            return data.get(value);
        }

        @JsonValue
        public int value() {
            return value;
        }
    }
}
