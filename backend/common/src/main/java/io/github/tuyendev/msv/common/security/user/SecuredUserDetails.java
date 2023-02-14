package io.github.tuyendev.msv.common.security.user;

import java.util.Collection;
import java.util.Objects;

import io.github.tuyendev.msv.common.constant.EntityStatus;
import one.util.streamex.StreamEx;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecuredUserDetails implements UserDetails {

	private final SecuredUser user;

	private SecuredUserDetails(@NonNull SecuredUser user) {
		this.user = user;
	}

	public static SecuredUserDetails instance(SecuredUser user) {
		return new SecuredUserDetails(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return StreamEx.of(user.authorityNames())
				.map(SimpleGrantedAuthority::new)
				.toImmutableList();
	}

	@Override
	public String getPassword() {
		return user.password();
	}

	@Override
	public String getUsername() {
		return user.username();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return Objects.equals(this.user.locked(), EntityStatus.UNLOCKED);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return Objects.equals(user.enabled(), EntityStatus.ENABLED);
	}

	public SecuredUser user() {
		return user;
	}
}
