package io.github.tuyendev.msv.common.service.auth;

import io.github.tuyendev.msv.common.security.user.DomainUserDetailsService;
import io.github.tuyendev.msv.common.security.user.SecuredUserDetails;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class JpaDomainUserDetailsService implements DomainUserDetailsService {


    @Override
    public SecuredUserDetails loadUserByUserId(Long userId) {
        return null;
    }

    @Override
    public SecuredUserDetails loadUserByPreferredUsername(String preferredUsername) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
