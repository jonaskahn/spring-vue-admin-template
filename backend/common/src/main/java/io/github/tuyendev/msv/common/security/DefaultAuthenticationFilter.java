package io.github.tuyendev.msv.common.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

import io.github.tuyendev.msv.common.security.jwt.JwtTokenProvider;
import io.github.tuyendev.msv.common.utils.DataProcessor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import static io.github.tuyendev.msv.common.constant.Authorization.AUTHORIZATION_HEADER;

@Slf4j
public class DefaultAuthenticationFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;

	private final AuthenticationEntryPoint authenticationEntryPoint;

	public DefaultAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AuthenticationEntryPoint authenticationEntryPoint) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		doFilterInternal(request, response, chain);
	}

	private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		this.logger.debug("Start default app authentication flow");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String jwt = resolveToken(httpServletRequest);
		if (StringUtils.hasText(jwt) && jwtTokenProvider.isSelfIssuer(jwt)) {
			try {
				this.jwtTokenProvider.authorizeToken(jwt);
				chain.doFilter(new HiddenTokenRequestWrapper((HttpServletRequest) request), response);
			}
			catch (AuthenticationException e) {
				SecurityContextHolder.clearContext();
				this.logger.trace("Failed to process authentication request", e);
				this.authenticationEntryPoint.commence((HttpServletRequest) request, (HttpServletResponse) response, e);
			}
		}
		else {
			chain.doFilter(request, response);
		}
		this.logger.debug("Finished default app authentication flow");
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		return DataProcessor.extractValueFromBearerToken(bearerToken);
	}


	static class HiddenTokenRequestWrapper extends HttpServletRequestWrapper {
		/**
		 * construct a wrapper for this request
		 *
		 * @param request
		 */
		public HiddenTokenRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getHeader(String name) {
			return Objects.equals(name, AUTHORIZATION_HEADER) ? null : super.getHeader(name);
		}

		/**
		 * get the Header names
		 */
		@Override
		public Enumeration<String> getHeaderNames() {
			return super.getHeaderNames();
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			return Objects.equals(name, AUTHORIZATION_HEADER) ? null : super.getHeaders(name);
		}
	}
}
