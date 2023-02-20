package io.github.tuyendev.msv.common.configurer;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import io.github.tuyendev.msv.common.constant.AuthorityType;
import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.constant.GroupEntity;
import io.github.tuyendev.msv.common.constant.UserEntity;
import io.github.tuyendev.msv.common.entity.Authority;
import io.github.tuyendev.msv.common.entity.Group;
import io.github.tuyendev.msv.common.entity.User;
import io.github.tuyendev.msv.common.exception.ShouldNeverOccurException;
import io.github.tuyendev.msv.common.repository.AuthorityRepository;
import io.github.tuyendev.msv.common.repository.GroupRepository;
import io.github.tuyendev.msv.common.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import static io.github.tuyendev.msv.common.constant.BeanName.BootstrapAppConfigurerBean;
import static io.github.tuyendev.msv.common.constant.GroupEntity.Type.defaultGroupIds;

@Slf4j
@Configuration(value = BootstrapAppConfigurerBean)
@RequiredArgsConstructor
public class BootstrapAppConfigurer {

	protected final PlatformTransactionManager txManager;

	private final Environment env;

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepo;

	private final AuthorityRepository authorityRepo;

	private final GroupRepository groupRepo;

	private static boolean isNecessaryUpdateAuthority(AuthorityType authorityType, Authority auth) {
		if (!Objects.equals(authorityType.value(), auth.getName())) {
			return true;
		}
		return !Objects.equals(authorityType.desc(), auth.getDescription());
	}

	private static boolean isNecessaryUpdateGroup(GroupEntity.Type type, Group group) {
		if (!Objects.equals(type.getName(), group.getName())) {
			return true;
		}
		if (!Objects.equals(type.getDesc(), group.getDescription())) {
			return true;
		}
		return Objects.isNull(group.getStatus()) || !Objects.equals(group.getStatus(), EntityStatus.ACTIVE);
	}

	@PostConstruct
	public void afterInit() {
		TransactionTemplate tx = new TransactionTemplate(txManager);
		tx.executeWithoutResult(txStatus -> {
			createDefaultAuthorities();
			createDefaultGroups();
			createDefaultUser();
		});

	}

	private void createDefaultGroups() {
		Map<Long, Group> existedGroups = StreamEx.of(groupRepo.findByIdIsIn(defaultGroupIds))
				.toMap(Group::getId, Function.identity());
		for (GroupEntity.Type value : GroupEntity.Type.values()) {
			Group found = existedGroups.get(value.getId());
			if (Objects.nonNull(found)) {
				if (isNecessaryUpdateGroup(value, found)) {
					found.setName(value.getName());
					found.setDescription(value.getDesc());
					found.setStatus(EntityStatus.ACTIVE);
					groupRepo.save(found);
					log.debug(String.format("Group [ %s ] updated", value.name()));
				}
			}
			else {
				Group group = Group.builder()
						.id(value.getId())
						.name(value.getName())
						.description(value.getDesc())
						.status(EntityStatus.ACTIVE)
						.build();
				groupRepo.save(group);
				log.debug(String.format("Group [ %s ] created", value.name()));
			}
		}

	}

	private void createDefaultAuthorities() {
		Map<String, Authority> existedAuthorities = StreamEx.of(authorityRepo.findByNameIsIn(AuthorityType.names()))
				.toMap(Authority::getName, Function.identity());
		for (AuthorityType auth : AuthorityType.values()) {
			Authority found = existedAuthorities.get(auth.value());
			if (Objects.nonNull(found)) {
				if (isNecessaryUpdateAuthority(auth, found)) {
					found.setName(auth.value());
					found.setDescription(auth.desc());
					authorityRepo.save(found);
					log.debug(String.format("Authority [ %s ] updated", auth.value()));
				}
			}
			else {
				Authority authority = Authority.builder()
						.name(auth.value())
						.description(auth.desc())
						.build();
				authorityRepo.save(authority);
				log.debug(String.format("Authority [ %s ] created", auth.value()));
			}
		}
	}

	private void createDefaultUser() {
		if (userRepo.nonExistedById(UserEntity.DEFAULT_USER_ADMIN_ID)) {
			final String defaultAdminUsername = env.getProperty("app.common.user.admin.username");
			final String defaultAdminPassword = env.getProperty("app.common.user.admin.password");
			final Instant now = Instant.now();
			final Set<Authority> authorities = StreamEx.of(authorityRepo.findAll()).toImmutableSet();
			final Group group = groupRepo.findById(GroupEntity.Type.ADMIN.getId()).orElseThrow(ShouldNeverOccurException::new);
			User admin = User.builder()
					.id(UserEntity.DEFAULT_USER_ADMIN_ID)
					.name(defaultAdminUsername)
					.unsignedName(defaultAdminUsername)
					.username(defaultAdminUsername)
					.email(defaultAdminUsername)
					.emailVerified(UserEntity.EMAIL_VERIFIED)
					.preferredUsername(UUID.randomUUID().toString())
					.password(passwordEncoder.encode(defaultAdminPassword))
					.enabled(EntityStatus.ENABLED)
					.locked(EntityStatus.UNLOCKED)
					.authorities(authorities)
					.groups(Set.of(group))
					.build();
			userRepo.save(admin);
			log.debug(String.format("User [ admin ]  created with password [ %s ]", defaultAdminPassword));
		}
	}
}
