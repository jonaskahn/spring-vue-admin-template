package io.github.tuyendev.msv.core.application.api;

import io.github.tuyendev.msv.common.constant.AuthorityType;
import io.github.tuyendev.msv.common.dto.Response;
import io.github.tuyendev.msv.core.application.dto.user.UserCreateRequestDto;
import io.github.tuyendev.msv.core.application.dto.user.UserRegisterRequestDto;
import io.github.tuyendev.msv.core.application.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
@Tag(name = "API handler User flow")
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/create")
    @Operation(summary = "Create user, only for admin role")
    @PreAuthorize("hasAuthority('" + AuthorityType.ADMIN_VALUE + "')")
    public Response createUser(@Valid @RequestBody UserCreateRequestDto request) {
        userService.create(request);
        return Response.ok();
    }

    @PostMapping(path = "/public/register")
    @Operation(summary = "Self register user")
    public Response registerUser(@Valid @RequestBody UserRegisterRequestDto request) {
        userService.register(request);
        return Response.ok();
    }
}
