package io.github.tuyendev.msv.common.utils;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import one.util.streamex.StreamEx;

import org.springframework.util.StringUtils;

import static io.github.tuyendev.msv.common.constant.Authorization.BEARER_TOKEN_PREFIX;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataProcessor {

	public static String joinsWithSpaceDelimiter(String... args) {
		return joins(" ", args);
	}

	public static String joins(String delimiter, String... args) {
		return StreamEx.of(args).filter(Objects::nonNull).joining(delimiter);
	}

	public static String extractValueFromBearerToken(String value) {
		if (StringUtils.hasText(value) && value.startsWith(BEARER_TOKEN_PREFIX)) {
			return value.substring(7);
		}
		return null;
	}
}
