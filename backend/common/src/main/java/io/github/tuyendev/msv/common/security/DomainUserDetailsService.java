package io.github.tuyendev.msv.common.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface DomainUserDetailsService extends UserDetailsService {

	SecuredUserDetails loadUserByUserId(final Long userId);

	SecuredUserDetails loadUserByPreferredUsername(final String preferredUsername);
}
