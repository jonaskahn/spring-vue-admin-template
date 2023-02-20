package io.github.tuyendev.msv.common.entity;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "access_tokens")
@SQLDelete(sql = "update access_tokens set status = 9 where id = ?")
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

	@NotNull
	@Column(name = "status", nullable = false)
	private Integer status;

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