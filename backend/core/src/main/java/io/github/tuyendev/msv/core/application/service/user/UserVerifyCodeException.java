package io.github.tuyendev.msv.core.application.service.user;

import io.github.tuyendev.msv.common.exception.LogicException;

public final class UserVerifyCodeException {

	private UserVerifyCodeException() {
	}

	public static class Creation extends LogicException {
		protected Creation() {
			super("app.user.exception.verify-code.cannot-create");
		}
	}

	public static class Verified extends LogicException {
		protected Verified() {
			super("app.user.exception.verify-code.verified");
		}
	}

	public static class Invalid extends LogicException {
		protected Invalid() {
			super("app.user.exception.verify-code.invalid");
		}
	}

}
