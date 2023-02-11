package io.github.tuyendev.msv.common.security.oauth2;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

public interface Oauth2JwtAuthenticationConverter extends Converter<Jwt, AbstractAuthenticationToken> {
}
