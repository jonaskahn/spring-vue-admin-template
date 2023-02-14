package io.github.tuyendev.msv.common.exception.jwt;

import org.springframework.security.core.AuthenticationException;

import static io.github.tuyendev.msv.common.utils.Translator.eval;

public class InvalidJwtTokenException extends AuthenticationException {

	public InvalidJwtTokenException() {
		super(eval("app.auth.exception.token-not-valid"));
	}
}
