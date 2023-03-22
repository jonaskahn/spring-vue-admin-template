package io.github.tuyendev.msv.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "access_tokens")
@EntityListeners(AuditingEntityListener.class)
public class AccessToken {
    @Id
    @Size(max = 255)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    @ToString.Exclude
    private User user;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expired_at")
    private Instant expiredAt;

    @OneToMany(mappedBy = "accessToken")
    @ToString.Exclude
    private Set<RefreshToken> refreshTokens = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccessToken that = (AccessToken) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}