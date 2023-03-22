package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Set<Group> findByIdIsIn(Collection<Long> ids);
}