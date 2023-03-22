package io.github.tuyendev.msv.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityStatus {

    public static final Integer INACTIVE = 0;

    public static final Integer ACTIVE = 1;

    public static final Integer DISABLED = INACTIVE;

    public static final Integer ENABLED = ACTIVE;

    public static final Integer LOCKED = 1;

    public static final Integer UNLOCKED = 0;

    public static final Integer DELETED = 9;

}
