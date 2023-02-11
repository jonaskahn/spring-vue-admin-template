package io.github.tuyendev.msv.common.security.user;

import io.github.tuyendev.msv.common.constant.EntityStatus;
import one.util.streamex.StreamEx;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

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
        return Objects.equals(this.user.getLocked(), EntityStatus.UNLOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(user.getEnabled(), EntityStatus.ENABLED);
    }

    public SecuredUser getUser() {
        return user;
    }
}
