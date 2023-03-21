package io.github.tuyendev.msv.core.application.service.user;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.naharoo.commons.mapstruct.MappingFacade;
import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.constant.GroupEntity;
import io.github.tuyendev.msv.common.entity.Group;
import io.github.tuyendev.msv.common.entity.User;
import io.github.tuyendev.msv.common.repository.GroupRepository;
import io.github.tuyendev.msv.common.repository.UserRepository;
import io.github.tuyendev.msv.common.utils.DataProcessor;
import io.github.tuyendev.msv.common.utils.PasswordGenerator;
import io.github.tuyendev.msv.core.application.dto.user.UserCreateRequestDto;
import io.github.tuyendev.msv.core.application.dto.user.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final MappingFacade mapper;

	private final UserRepository userRepo;

	private final GroupRepository groupRepo;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void create(UserCreateRequestDto request) {
		User user = mapper.map(request, User.class);
		user.setGroupIds(Set.of(GroupEntity.Type.USER.getId()));
		final String password = PasswordGenerator.generateStrongPassword();
		user.setRawPassword(password);
		user.setLocked(EntityStatus.UNLOCKED);
		createUserInternal(user);
		sendEmailNotify(user.getEmail(), password);
	}

	//TODO add mail
	private void sendEmailNotify(String email, String password) {
	}

	private void createUserInternal(User user) {
		if (userRepo.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
			throw new UserExistedException();
		}
		Set<Group> groups = groupRepo.findByIdIsIn(user.getGroupIds());
		user.setName(DataProcessor.joinsWithSpaceDelimiter(user.getGivenName(), user.getMiddleName(), user.getFamilyName()));
		user.setGroups(groups);
		user.setPreferredUsername(UUID.randomUUID().toString());
		user.setUnsignedName(StringUtils.stripAccents(user.getName()));
		user.setPassword(passwordEncoder.encode(user.getRawPassword()));
		user.setEnabled(EntityStatus.ENABLED);
		userRepo.save(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void register(UserRegisterRequestDto request) {
		User user = mapper.map(request, User.class);
		Set<Long> filteredGroupIds = StreamEx.of(request.getGroupIds())
				.filter(groupId -> !Objects.equals(groupId, GroupEntity.Type.ADMIN.getId()))
				.toImmutableSet();
		user.setGroupIds(filteredGroupIds);
		user.setLocked(EntityStatus.LOCKED);
		createUserInternal(user);
		sendEmailNotify(user.getEmail(), request.getRawPassword());
	}
}
