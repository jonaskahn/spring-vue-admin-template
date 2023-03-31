package io.github.tuyendev.msv.core.application.service.user;

import io.github.tuyendev.msv.common.exception.LogicException;

public final class UserVerifyCodeException {

	private UserVerifyCodeException() {
	}

	public static class Creation extends LogicException {
		protected Creation() {
			super("app.user.verify-account.exception.verify-code.fail-make-code");
		}
	}

	public static class Verified extends LogicException {
		protected Verified() {
			super("app.user.verify-account.exception.verify-code.verified");
		}
	}

	public static class Invalid extends LogicException {
		protected Invalid() {
			super("app.user.verify-account.exception.verify-code.invalid");
		}
	}

}
