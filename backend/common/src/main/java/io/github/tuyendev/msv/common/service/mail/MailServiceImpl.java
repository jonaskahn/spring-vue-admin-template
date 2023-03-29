package io.github.tuyendev.msv.common.service.mail;

import java.nio.charset.StandardCharsets;

import io.github.tuyendev.msv.common.dto.mail.EmailContentDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {


	private final JavaMailSender emailSender;

	private final SpringTemplateEngine templateEngine;

	@Override
	public void send(EmailContentDto mail) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariables(mail.getProps());
			String html = templateEngine.process(mail.getTemplate(), context);
			helper.setTo(mail.getTo());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFrom());
			emailSender.send(message);
		}
		catch (MessagingException e) {
			throw new MailFormatException();
		}
	}
}
