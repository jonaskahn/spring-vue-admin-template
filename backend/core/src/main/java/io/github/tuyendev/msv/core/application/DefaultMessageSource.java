package io.github.tuyendev.msv.core.application;

import io.github.tuyendev.msv.common.annotation.MessageResourceClaim;

import org.springframework.stereotype.Component;

@Component
public class DefaultMessageSource implements MessageResourceClaim {
	@Override
	public String[] messagesSources() {
		return new String[]{"classpath:messages"};
	}
}
