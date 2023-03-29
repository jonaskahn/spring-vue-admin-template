package io.github.tuyendev.msv.common.repository;

import java.util.Collection;
import java.util.Set;

import io.github.tuyendev.msv.common.entity.Group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

	Set<Group> findByIdIsIn(Collection<Long> ids);
}