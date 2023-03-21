package io.github.tuyendev.msv.common.service.oauth2;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import io.github.tuyendev.msv.common.constant.AuthorityType;
import io.github.tuyendev.msv.common.constant.Authorization;
import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.constant.UserEntity;
import io.github.tuyendev.msv.common.entity.Authority;
import io.github.tuyendev.msv.common.entity.User;
import io.github.tuyendev.msv.common.exception.ShouldNeverOccurException;
import io.github.tuyendev.msv.common.repository.AuthorityRepository;
import io.github.tuyendev.msv.common.repository.UserRepository;
import io.github.tuyendev.msv.common.security.oauth2.Oauth2JwtAuthenticationConverter;
import io.github.tuyendev.msv.common.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KeyloakOauth2JwtAuthenticationConverter implements Oauth2JwtAuthenticationConverter {

	private final UserDetailsChecker postCheckUserStatus = new AccountStatusUserDetailsChecker();

	private final UserDetailsService userDetailsService;

	private final UserRepository userRepo;

	private final PasswordEncoder passwordEncoder;

	private final AuthorityRepository authorityRepository;

	private static boolean isNecessarilyUpdate(Jwt jwt, User existedUser) {
		if (!Objects.equals(jwt.getClaimAsString(Authorization.JwtClaim.FAMILY_NAME), existedUser.getFamilyName())) {
			return true;
		}
		if (!Objects.equals(jwt.getClaimAsString(Authorization.JwtClaim.GIVEN_NAME), existedUser.getGivenName())) {
			return true;
		}
		if (!Objects.equals(jwt.getClaimAsString(Authorization.JwtClaim.NAME), existedUser.getName())) {
			return true;
		}
		return !Objects.equals(buildUsernameFromJwt(jwt), existedUser.getUsername());
	}

	private static String buildUsernameFromJwt(Jwt jwt) {
		return "keyloak_" + jwt.getClaimAsString(Authorization.JwtClaim.PREFERRED_USERNAME);
	}

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		processUpdateUserByJwtToken(jwt);
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getClaimAsString(Authorization.JwtClaim.EMAIL));
		postCheckUserStatus.check(userDetails);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	private void processUpdateUserByJwtToken(Jwt jwt) {
		final String email = jwt.getClaimAsString(Authorization.JwtClaim.EMAIL);
		userRepo.findUserByEmail(email).ifPresentOrElse(existeduser -> updateInternalUser(jwt, existeduser), () -> createInternalUser(jwt));

	}

	private void createInternalUser(Jwt jwt) {
		Authority authority = authorityRepository.findAuthorityByName(AuthorityType.USER.value())
				.orElseThrow(ShouldNeverOccurException::new);
		User user = User.builder()
				.email(jwt.getClaimAsString(Authorization.JwtClaim.EMAIL))
				.emailVerified(UserEntity.EMAIL_VERIFIED)
				.username(buildUsernameFromJwt(jwt))
				.preferredUsername(UUID.randomUUID().toString())
				.familyName(jwt.getClaimAsString(Authorization.JwtClaim.FAMILY_NAME))
				.givenName(jwt.getClaimAsString(Authorization.JwtClaim.GIVEN_NAME))
				.name(jwt.getClaimAsString(Authorization.JwtClaim.NAME))
				.password(passwordEncoder.encode(PasswordGenerator.generateStrongPassword()))
				.enabled(EntityStatus.ENABLED)
				.locked(EntityStatus.UNLOCKED)
				.authorities(Set.of(authority))
				.build();
		userRepo.save(user);
	}

	private void updateInternalUser(Jwt jwt, User existedUser) {
		if (isNecessarilyUpdate(jwt, existedUser)) {
			existedUser.setFamilyName(jwt.getClaimAsString(Authorization.JwtClaim.FAMILY_NAME));
			existedUser.setGivenName(jwt.getClaimAsString(Authorization.JwtClaim.GIVEN_NAME));
			existedUser.setName(jwt.getClaimAsString(Authorization.JwtClaim.NAME));
			existedUser.setUsername(buildUsernameFromJwt(jwt));
			userRepo.save(existedUser);
		}

	}
}
