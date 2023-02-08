package io.github.tuyendev.msv.common.configurer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
class PasswordEncoderConfigurer {

	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		return new DelegatingPasswordEncoder("bcrypt", encoders);
	}
}
