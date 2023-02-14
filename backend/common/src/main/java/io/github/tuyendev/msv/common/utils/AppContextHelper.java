package io.github.tuyendev.msv.common.utils;

import java.util.Optional;

import io.github.tuyendev.msv.common.constant.UserEntity;
import io.github.tuyendev.msv.common.security.user.SecuredUser;
import io.github.tuyendev.msv.common.security.user.SecuredUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AppContextHelper {

	private static ApplicationContext applicationContext;

	@Autowired
	public AppContextHelper(ApplicationContext applicationContext) {
		AppContextHelper.applicationContext = applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public static Optional<SecuredUser> getCurrentLoginUser() {
		return Optional.of(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.map(authentication -> authentication instanceof AnonymousAuthenticationToken ?
						SecuredUser.ANONYMOUS_USER : authentication.getPrincipal())
				.map(SecuredUserDetails.class::cast)
				.map(SecuredUserDetails::user);
	}

	public static Long getCurrentLoginUserId() {
		return getCurrentLoginUser().map(SecuredUser::id).orElse(UserEntity.DEFAULT_USER_ADMIN_ID);
	}

}