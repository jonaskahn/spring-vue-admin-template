package io.github.tuyendev.msv.common.api;

import io.github.tuyendev.msv.common.constant.Authorization;
import io.github.tuyendev.msv.common.dto.Response;
import io.github.tuyendev.msv.common.dto.token.RenewTokenRequestDto;
import io.github.tuyendev.msv.common.dto.token.TokenInfoDto;
import io.github.tuyendev.msv.common.dto.token.TokenInfoRequestDto;
import io.github.tuyendev.msv.common.dto.token.TokenRequestDto;
import io.github.tuyendev.msv.common.security.jwt.JwtAccessToken;
import io.github.tuyendev.msv.common.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "API provide authenticate, password reset and forgot flows.")
@RestController
@RequestMapping(path = "/auth")
public class AuthorizationHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(name = "Authorization entry point", path = "/token")
    @Operation(summary = "Login API, generate token if success")
    public Response<JwtAccessToken> authorize(@Valid @RequestBody TokenRequestDto request) {
        JwtAccessToken token = jwtTokenProvider.generateToken(request.getUsername(), request.getPassword(), request.isRememberMe());
        return Response.ok(token, "app.auth.message.success");
    }

    @PostMapping(name = "Renew token", path = "/token/renew")
    @Operation(summary = "Regenerate token if refresh token is valid")
    public Response<JwtAccessToken> reauthorize(@Valid @RequestBody RenewTokenRequestDto request) {
        JwtAccessToken token = jwtTokenProvider.renewToken(request.getRefreshToken());
        return Response.ok(token);
    }

    @PostMapping(name = "Show token info", path = "/token/info")
    @Operation(summary = "Show the token information")
    public Response<TokenInfoDto> getTokenInfo(@Valid @RequestBody TokenInfoRequestDto request) {
        TokenInfoDto info = jwtTokenProvider.getTokenInfo(request.getToken());
        return Response.ok(info);
    }

    @DeleteMapping(name = "Show token info", path = "/token/revoke")
    @Operation(summary = "Show the token information")
    public Response<TokenInfoDto> revoke(@RequestAttribute(Authorization.BEARER_TOKEN_VALUE) String jwt) {
        jwtTokenProvider.revokeToken(jwt);
        return Response.ok();
    }
}
