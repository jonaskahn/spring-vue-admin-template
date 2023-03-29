package io.github.tuyendev.msv.common.service.mail;

import io.github.tuyendev.msv.common.exception.LogicException;

public class MailFormatException extends LogicException {
	protected MailFormatException() {
		super("Can not send the email");
	}
}
