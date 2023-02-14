package io.github.tuyendev.msv.common.api;

import io.github.tuyendev.msv.common.dto.Response;
import io.github.tuyendev.msv.common.dto.auth.AuthorizationRequestDto;
import io.github.tuyendev.msv.common.dto.auth.RefreshTokenRequestDto;
import io.github.tuyendev.msv.common.security.jwt.JwtAccessToken;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "API provide authenticate, password reset and forgot flows.")
@RestController
@RequestMapping(path = "/auth")
public class AuthorizationHandler {

	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping(name = "Authorization entry point", path = "/token")
	@Operation(summary = "Login API, generate token if success")
	public Response<JwtAccessToken> authorize(@Valid @RequestBody AuthorizationRequestDto request) {
		JwtAccessToken token = jwtTokenProvider.generateToken(request.getUsername(), request.getPassword(), request.isRememberMe());
		return Response.ok(token);
	}

	@PostMapping(name = "Renew token entry point", path = "/token/renew")
	@Operation(summary = "Regenerate token if refresh token is valid")
	public Response<JwtAccessToken> reauthorize(@Valid @RequestBody RefreshTokenRequestDto request) {
		JwtAccessToken token = jwtTokenProvider.renewToken(request.getRefreshToken());
		return Response.ok(token);
	}
}
