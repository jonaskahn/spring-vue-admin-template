package io.github.tuyendev.msv.common.utils;

import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Translator {
	private static MessageSource messageSource;

	@Autowired
	Translator(MessageSource messageSource) {
		Translator.messageSource = messageSource;
	}

	public static String eval(String message, Object... args) {
		Locale locale = LocaleContextHolder.getLocale();
		try {
			return messageSource.getMessage(message, args, locale);
		}
		catch (NoSuchMessageException ignoredException) {
			log.warn("No message found for key: {} ", message);
			return message;
		}
	}
}