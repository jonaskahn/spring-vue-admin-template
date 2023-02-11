package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default boolean nonExistedById(final Long id) {
        return !existsById(id);
    }

    Optional<User> findUserByEmailOrUsername(String email, String username);
}