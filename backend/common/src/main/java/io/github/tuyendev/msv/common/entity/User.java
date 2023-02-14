package io.github.tuyendev.msv.common.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
import org.hibernate.annotations.GenericGenerator;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id", nullable = false)
	@GenericGenerator(name = "UseExistingIdOtherwiseGenerateId",
			strategy = "io.github.tuyendev.msv.common.entity.extras.UseExistingIdOtherwiseGenerateId")
	@GeneratedValue(generator = "UseExistingIdOtherwiseGenerateId")
	private Long id;

	@Size(max = 255)
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Instant createdDate;

	@Size(max = 255)
	@Column(name = "last_modified_by")
	private String lastModifiedBy;

	@Column(name = "last_modified_date")
	private Instant lastModifiedDate;

	@Column(name = "birthdate")
	private LocalDate birthdate;

	@Size(max = 255)
	@NotNull
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "email_verified")
	private Integer emailVerified;

	@Column(name = "enabled")
	private Integer enabled;

	@Column(name = "locked")
	private Integer locked;

	@Size(max = 255)
	@Column(name = "family_name")
	private String familyName;

	@Column(name = "gender")
	private Integer gender;

	@Size(max = 255)
	@Column(name = "given_name")
	private String givenName;

	@Size(max = 255)
	@Column(name = "middle_name")
	private String middleName;

	@Size(max = 255)
	@Column(name = "name")
	private String name;

	@Size(max = 255)
	@Column(name = "password")
	private String password;

	@Size(max = 255)
	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "phone_number_verified")
	private Integer phoneNumberVerified;

	@Size(max = 255)
	@NotNull
	@Column(name = "preferred_username", nullable = false)
	private String preferredUsername;

	@Size(max = 255)
	@Column(name = "unsigned_name")
	private String unsignedName;

	@Size(max = 255)
	@NotNull
	@Column(name = "username", nullable = false)
	private String username;

	@ManyToMany
	@JoinTable(name = "group_members",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "group_id"))
	@ToString.Exclude
	private Set<Group> groups = new LinkedHashSet<>();

	@ManyToMany
	@JoinTable(name = "user_authorities",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id"))
	@ToString.Exclude
	private Set<Authority> authorities = new LinkedHashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		User user = (User) o;
		return id != null && Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", createdBy='" + createdBy + '\'' +
				", createdDate=" + createdDate +
				", lastModifiedBy='" + lastModifiedBy + '\'' +
				", lastModifiedDate=" + lastModifiedDate +
				", birthdate=" + birthdate +
				", emailVerified=" + emailVerified +
				", enabled=" + enabled +
				", locked=" + locked +
				", familyName='" + familyName + '\'' +
				", gender=" + gender +
				", givenName='" + givenName + '\'' +
				", middleName='" + middleName + '\'' +
				", name='" + name + '\'' +
				", phoneNumberVerified=" + phoneNumberVerified +
				", preferredUsername='" + preferredUsername + '\'' +
				", unsignedName='" + unsignedName + '\'' +
				", username='" + username + '\'' +
				'}';
	}
}