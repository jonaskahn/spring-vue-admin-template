package io.github.tuyendev.msv.common.exception;

public class UserNotVerifyException extends LogicException {
	public UserNotVerifyException() {
		super("app.user.exception.not-verified");
	}
}
