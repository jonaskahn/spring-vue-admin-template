package io.github.tuyendev.msv.common.service.mail;

import io.github.tuyendev.msv.common.dto.mail.EmailContentDto;

public interface MailService {

	void send(EmailContentDto mail);
}
