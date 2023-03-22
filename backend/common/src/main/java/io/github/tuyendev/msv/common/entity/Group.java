package io.github.tuyendev.msv.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
@EntityListeners(AuditingEntityListener.class)
public class Group extends AuditableEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateId",
            strategy = "io.github.tuyendev.msv.common.entity.extras.UseExistingIdOtherwiseGenerateId")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateId")
    private Long id;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToMany
    @JoinTable(name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group group = (Group) o;
        return id != null && Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}