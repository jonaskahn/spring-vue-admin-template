package io.github.tuyendev.msv.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_tokens")
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken {
    @Id
    @Size(max = 255)
    @Column(name = "id", nullable = false)
    private String id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "access_token_id", nullable = false, insertable = false, updatable = false)
    @ToString.Exclude
    private AccessToken accessToken;

    @NotNull
    @Column(name = "access_token_id")
    private String accessTokenId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "expired_at")
    private Instant expiredAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RefreshToken that = (RefreshToken) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}