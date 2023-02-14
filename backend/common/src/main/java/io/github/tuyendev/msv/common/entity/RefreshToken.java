package io.github.tuyendev.msv.common.entity;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_tokens")
@SQLDelete(sql = "update refresh_tokens set status = 9 where id = ?")
public class RefreshToken {
	@Id
	@Size(max = 255)
	@Column(name = "id", nullable = false)
	private String id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "access_token_id", nullable = false, insertable = false, updatable = false)
	@ToString.Exclude
	private AccessToken accessToken;

	@Column(name = "access_token_id")
	private String accessTokenId;

	@NotNull
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "expired_at")
	private Instant expiredAt;

	@NotNull
	@Column(name = "status", nullable = false)
	private Integer status;

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