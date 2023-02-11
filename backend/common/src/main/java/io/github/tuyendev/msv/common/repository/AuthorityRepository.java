package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}