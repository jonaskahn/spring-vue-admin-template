package io.github.tuyendev.msv.common.service.auth;

import com.naharoo.commons.mapstruct.MappingFacade;
import io.github.tuyendev.msv.common.constant.UserEntity;
import io.github.tuyendev.msv.common.dto.user.SecuredUserDto;
import io.github.tuyendev.msv.common.entity.User;
import io.github.tuyendev.msv.common.exception.UserNotExistedException;
import io.github.tuyendev.msv.common.exception.UserNotVerifyException;
import io.github.tuyendev.msv.common.repository.UserRepository;
import io.github.tuyendev.msv.common.security.user.DomainUserDetailsService;
import io.github.tuyendev.msv.common.security.user.SecuredUser;
import io.github.tuyendev.msv.common.security.user.SecuredUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Primary
public class JpaDomainUserDetailsService implements DomainUserDetailsService {

    private final UserRepository userRepo;

    private final MappingFacade mapper;

    @Override
    public SecuredUserDetails loadUserByUserId(Long userId) {
        User found = userRepo.findById(userId).orElseThrow(UserNotExistedException::new);
        return convertUserEntityToSecureUserDetails(found);
    }

    private SecuredUserDetails convertUserEntityToSecureUserDetails(User user) {
        postCheck(user);
        SecuredUser securedUser = mapper.map(user, SecuredUserDto.class);
        return SecuredUserDetails.instance(securedUser);
    }

    private void postCheck(User user) {
        if (!Objects.equals(user.getEmailVerified(), UserEntity.EmailVerify.VERIFIED.value())) {
            throw new UserNotVerifyException();
        }
    }

    @Override
    public SecuredUserDetails loadUserByPreferredUsername(String preferredUsername) {
        User found = userRepo.findUserByPreferredUsername(preferredUsername).orElseThrow(UserNotExistedException::new);
        return convertUserEntityToSecureUserDetails(found);
    }

    @Override
    public SecuredUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final String lowerCaseUsername = username.toLowerCase();
        User found = userRepo.findUserByEmailOrUsername(lowerCaseUsername, lowerCaseUsername).orElseThrow(UserNotExistedException::new);
        return convertUserEntityToSecureUserDetails(found);
    }
}
