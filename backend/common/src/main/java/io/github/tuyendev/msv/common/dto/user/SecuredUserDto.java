package io.github.tuyendev.msv.common.dto.user;

import io.github.tuyendev.msv.common.security.user.SecuredUser;
import lombok.Builder;

import java.util.Set;

@Builder
public record SecuredUserDto(Long id, String username, String password, String preferredUsername,
                             Integer enabled, Integer locked, Set<String> authorityNames) implements SecuredUser {
}
