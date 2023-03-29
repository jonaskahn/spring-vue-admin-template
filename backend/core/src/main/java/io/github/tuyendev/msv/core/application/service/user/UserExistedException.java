package io.github.tuyendev.msv.core.application.service.user;

import io.github.tuyendev.msv.common.exception.LogicException;

public class UserExistedException extends LogicException {

	public UserExistedException() {
		super("app.user.exception.existed-user");
	}
}
