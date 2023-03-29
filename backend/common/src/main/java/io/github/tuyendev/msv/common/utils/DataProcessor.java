package io.github.tuyendev.msv.common.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Objects;

import io.github.tuyendev.msv.common.constant.DefaultInstance;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import one.util.streamex.StreamEx;

import org.springframework.context.i18n.LocaleContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataProcessor {

	public static String joinsWithSpaceDelimiter(String... args) {
		return joins(" ", args);
	}

	public static String joins(String delimiter, String... args) {
		return StreamEx.of(args).filter(Objects::nonNull).joining(delimiter);
	}

	public static String getTemplateByLocale(String template) {
		return template + "_" + LocaleContextHolder.getLocale() + ".html";
	}


	public static byte[] encrypt(final String data, final String key) throws GeneralSecurityException, IOException {
		return DefaultInstance.CRYPTO.encrypt(getUTF8Bytes(data), key.toCharArray());
	}

	public static byte[] decrypt(final String data, final String key) throws GeneralSecurityException {
		return decrypt(getUTF8Bytes(data), key);
	}

	public static byte[] decrypt(final byte[] data, final String key) throws GeneralSecurityException {
		return DefaultInstance.CRYPTO.decrypt(data, key.toCharArray());
	}

	public static byte[] getUTF8Bytes(final String input) {
		return input.getBytes(StandardCharsets.UTF_8);
	}

}
