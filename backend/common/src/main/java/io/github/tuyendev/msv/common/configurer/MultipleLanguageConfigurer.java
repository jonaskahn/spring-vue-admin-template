package io.github.tuyendev.msv.common.configurer;

import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;


@Configuration
@RequiredArgsConstructor
class MultipleLanguageConfigurer extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

	private static final List<Locale> SUPPORTED_LOCALES = List.of(new Locale("vi"), new Locale("en"));

	private final MessageSource messageSource;

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String headerLang = request.getHeader("Accept-Language");
		return headerLang == null || headerLang.isEmpty() ? Locale.getDefault()
				: Locale.lookup(Locale.LanguageRange.parse(headerLang), SUPPORTED_LOCALES);
	}

	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource);
		return validator;
	}
}
