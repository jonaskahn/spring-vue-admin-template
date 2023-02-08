package io.github.tuyendev.msv.common.security;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import io.github.tuyendev.msv.common.CommonConstants;
import one.util.streamex.StreamEx;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecuredUserDetails implements UserDetails {

	private final SecuredUser user;

	private final String principal;

	public SecuredUserDetails(@NonNull SecuredUser user, @NonNull String principal) {
		this.user = user;
		this.principal = principal;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return StreamEx.of(user.getAuthorityNames())
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return principal;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return Objects.equals(this.user.getLocked(), CommonConstants.EntityStatus.UNLOCKED);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return Objects.equals(user.getEnabled(), CommonConstants.EntityStatus.ACTIVE);
	}

	public SecuredUser getUser() {
		return user;
	}
}
