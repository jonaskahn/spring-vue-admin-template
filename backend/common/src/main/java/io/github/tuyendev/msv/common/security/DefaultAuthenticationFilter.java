package io.github.tuyendev.msv.common.security;

import io.github.tuyendev.msv.common.constant.Authorization;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenProvider;
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

import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

import static io.github.tuyendev.msv.common.constant.Authorization.AUTHORIZATION_HEADER;
import static io.github.tuyendev.msv.common.constant.Authorization.BEARER_TOKEN_PREFIX;

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
                request.setAttribute(Authorization.BEARER_TOKEN_VALUE, jwt);
                this.jwtTokenProvider.authorizeToken(jwt);
                chain.doFilter(new HiddenTokenRequestWrapper((HttpServletRequest) request), response);
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                this.logger.trace("Failed to process authentication request", e);
                this.authenticationEntryPoint.commence((HttpServletRequest) request, (HttpServletResponse) response, e);
            }
        } else {
            chain.doFilter(request, response);
        }
        this.logger.debug("Finished default app authentication flow");
    }

    private String resolveToken(HttpServletRequest request) {
        String value = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(value) && value.startsWith(BEARER_TOKEN_PREFIX)) {
            return value.substring(7);
        }
        return null;
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
