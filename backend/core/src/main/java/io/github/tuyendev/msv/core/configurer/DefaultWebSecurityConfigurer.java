package io.github.tuyendev.msv.core.configurer;


import io.github.tuyendev.msv.common.security.RestAuthenticationEntryPoint;
import io.github.tuyendev.msv.common.security.jwt.JwtSecurityAdapter;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenProvider;
import io.github.tuyendev.msv.common.security.oauth2.Oauth2JwtAuthenticationConverter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class DefaultWebSecurityConfigurer {

	private static final String[] IGNORED_API = new String[] {
			"/webjars/**", "/error/**",
			"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
			"/auth/token",
			"/auth/token/renew",
			"/auth/password/forgot",
			"/auth/password/forgot-complete",
			"/auth/password/reset",
			"/auth/password/reset-complete"
	};

	private final JwtTokenProvider jwtTokenProvider;

	private final Oauth2JwtAuthenticationConverter oauth2JwtAuthenticationConverter;

	private final HandlerExceptionResolver resolver;

	public DefaultWebSecurityConfigurer(JwtTokenProvider jwtTokenProvider,
			Oauth2JwtAuthenticationConverter oauth2JwtAuthenticationConverter,
			@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.oauth2JwtAuthenticationConverter = oauth2JwtAuthenticationConverter;
		this.resolver = resolver;
	}


	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers(IGNORED_API);
	}

	@Bean
	public SecurityFilterChain web(HttpSecurity http) throws Exception {
		// @formatter:off
		http.cors().and().csrf().disable()
				.headers()
				.referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
				.and()
				.permissionsPolicy()
				.policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
				.and()
				.frameOptions()
				.sameOrigin()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeHttpRequests(requests -> requests.
				anyRequest().permitAll());

		http.formLogin().disable()
				.logout().disable()
				.httpBasic().disable()
				.apply(securityConfigurerAdapter());
		http.oauth2ResourceServer(oauth2 -> {
			oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(oauth2JwtAuthenticationConverter));
			oauth2.authenticationEntryPoint(new RestAuthenticationEntryPoint(resolver));
		});
		http.exceptionHandling()
				.authenticationEntryPoint(new RestAuthenticationEntryPoint(resolver));
		return http.build();
		// @formatter:on
	}

	private JwtSecurityAdapter securityConfigurerAdapter() {
		return new JwtSecurityAdapter(jwtTokenProvider, new RestAuthenticationEntryPoint(resolver));
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOriginPattern("*");
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
