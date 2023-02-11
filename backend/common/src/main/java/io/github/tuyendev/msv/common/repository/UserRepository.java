package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}