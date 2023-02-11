package io.github.tuyendev.msv.common.repository;

import io.github.tuyendev.msv.common.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}