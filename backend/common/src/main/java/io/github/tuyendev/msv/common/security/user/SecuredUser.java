package io.github.tuyendev.msv.common.security.user;

import java.io.Serializable;
import java.util.Set;

import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.constant.UserEntity;

public interface SecuredUser extends Serializable {

	SecuredUser ANONYMOUS_USER = new SecuredUser() {
		private static final String ANONYMOUS_USER = "anonymous";

		@Override
		public Long id() {
			return UserEntity.DEFAULT_USER_ANONYMOUS_ID;
		}

		@Override
		public String username() {
			return ANONYMOUS_USER;
		}

		@Override
		public String preferredUsername() {
			return ANONYMOUS_USER;
		}

		@Override
		public String password() {
			return null;
		}

		@Override
		public Integer enabled() {
			return EntityStatus.ENABLED;
		}

		@Override
		public Integer locked() {
			return EntityStatus.UNLOCKED;
		}

		@Override
		public Set<String> authorityNames() {
			return Set.of();
		}
	};

	Long id();

	String username();

	String preferredUsername();

	String password();

	Integer enabled();

	Integer locked();

	Set<String> authorityNames();
}
