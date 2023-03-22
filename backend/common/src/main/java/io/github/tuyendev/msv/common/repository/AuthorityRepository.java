package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    boolean existsByName(final String name);

    default boolean nonExistedByName(final String name) {
        return !existsByName(name);
    }

    Optional<Authority> findAuthorityByName(final String name);

    List<Authority> findByNameIsIn(Collection<String> names);
}