package io.github.tuyendev.msv.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import one.util.streamex.StreamEx;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataProcessor {

    public static String joinsWithSpaceDelimiter(String... args) {
        return joins(" ", args);
    }

    public static String joins(String delimiter, String... args) {
        return StreamEx.of(args).filter(Objects::nonNull).joining(delimiter);
    }
}
