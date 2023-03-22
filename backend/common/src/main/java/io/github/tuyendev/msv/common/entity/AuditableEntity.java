package io.github.tuyendev.msv.common.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@MappedSuperclass
public abstract class AuditableEntity {

    @Column(name = "created_by")
    @CreatedBy
    protected Long createdBy;

    @Column(name = "created_date")
    @CreatedDate
    protected Instant createdDate;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    protected Long lastModifiedBy;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    protected Instant lastModifiedDate;
}
