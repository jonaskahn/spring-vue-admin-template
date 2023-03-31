package io.github.tuyendev.msv.common.configurer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import io.github.tuyendev.msv.common.annotation.MessageResourceClaim;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@RequiredArgsConstructor
public class MessageSourcesConfigurer {

	private final List<MessageResourceClaim> instructors;

	@Bean
	public MessageSource messageSource() {
		var sourcePaths = StreamEx.of(instructors)
				.map(MessageResourceClaim::messagesSources)
				.flatMap(Arrays::stream)
				.toArray(String.class);

		var messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(sourcePaths);
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		return messageSource;
	}

}
