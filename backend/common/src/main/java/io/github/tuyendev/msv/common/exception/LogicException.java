package io.github.tuyendev.msv.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.github.tuyendev.msv.common.utils.Translator.eval;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class LogicException extends RuntimeException {

	public LogicException(String message, Object... args) {
		super(eval(message, args));
	}

	public LogicException(Throwable cause, String message, Object... args) {
		super(eval(message, args), cause);
	}
}
