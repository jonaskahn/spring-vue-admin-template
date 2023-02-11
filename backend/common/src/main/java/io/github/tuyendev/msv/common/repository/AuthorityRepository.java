package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    boolean existsByName(final String name);

    default boolean nonExistedByName(final String name) {
        return !existsByName(name);
    }
}