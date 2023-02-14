package io.github.tuyendev.msv.common.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static io.github.tuyendev.msv.common.utils.Translator.eval;

public class UserNotExistedException extends UsernameNotFoundException {

	public UserNotExistedException() {
		super(eval("app.user.exception.not-found"));
	}
}
