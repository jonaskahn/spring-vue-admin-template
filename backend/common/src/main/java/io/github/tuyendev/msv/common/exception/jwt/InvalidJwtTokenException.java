package io.github.tuyendev.msv.common.exception.jwt;

import io.github.tuyendev.msv.common.CommonMessageSource;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.AuthenticationException;

public class InvalidJwtTokenException extends AuthenticationException {

	private static final MessageSourceAccessor messageSource = CommonMessageSource.getAccessor();

	public InvalidJwtTokenException() {
		super(messageSource.getMessage("app.auth.exception.token-not-valid"));
	}
}
