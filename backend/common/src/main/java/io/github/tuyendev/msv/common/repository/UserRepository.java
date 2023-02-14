package io.github.tuyendev.msv.common.repository;

import java.util.Optional;

import io.github.tuyendev.msv.common.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	default boolean nonExistedById(final Long id) {
		return !existsById(id);
	}

	Optional<User> findUserByEmailOrUsername(String email, String username);

	Optional<User> findUserByEmail(String email);

	Optional<User> findUserByPreferredUsername(String preferredUsername);
}