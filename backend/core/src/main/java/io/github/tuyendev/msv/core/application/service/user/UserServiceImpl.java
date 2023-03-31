package io.github.tuyendev.msv.core.application.service.user;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.naharoo.commons.mapstruct.MappingFacade;
import io.github.tuyendev.msv.common.constant.DefaultInstance;
import io.github.tuyendev.msv.common.constant.EntityStatus;
import io.github.tuyendev.msv.common.constant.GroupEntity;
import io.github.tuyendev.msv.common.constant.MailTemplate;
import io.github.tuyendev.msv.common.constant.UserEntity;
import io.github.tuyendev.msv.common.dto.mail.EmailContentDto;
import io.github.tuyendev.msv.common.entity.Group;
import io.github.tuyendev.msv.common.entity.User;
import io.github.tuyendev.msv.common.repository.GroupRepository;
import io.github.tuyendev.msv.common.repository.UserRepository;
import io.github.tuyendev.msv.common.service.mail.MailService;
import io.github.tuyendev.msv.common.utils.DataProcessor;
import io.github.tuyendev.msv.common.utils.PasswordGenerator;
import io.github.tuyendev.msv.core.application.constants.ExternalLink;
import io.github.tuyendev.msv.core.application.dto.user.UserCreateRequestDto;
import io.github.tuyendev.msv.core.application.dto.user.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static io.github.tuyendev.msv.common.utils.Translator.eval;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final MappingFacade mapper;

	private final UserRepository userRepo;

	private final GroupRepository groupRepo;

	private final PasswordEncoder passwordEncoder;

	private final MailService mailService;

	@Value(value = "${spring.mail.default-sender}")
	private String defaultMailSender;

	@Value(value = "${spring.application.secret-key}")
	private String appKey;

	@Value(value = "${spring.application.webapp-url}")
	private String frontendUrl;

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void create(UserCreateRequestDto request) {
		User user = mapper.map(request, User.class);
		Set<Long> filteredGroupIds = StreamEx.of(request.getGroupIds())
				.filter(groupId -> !Objects.equals(groupId, GroupEntity.Type.ADMIN.getId()))
				.toImmutableSet();
		user.setGroupIds(filteredGroupIds);
		final String password = PasswordGenerator.generateStrongPassword();
		user.setRawPassword(password);
		user.setLocked(EntityStatus.UNLOCKED);
		createUserInternal(user);
		sendNotify(user);
	}

	private void sendNotify(User user) {
		EmailContentDto email = EmailContentDto.builder()
				.from(defaultMailSender)
				.to(user.getEmail())
				.subject(eval("app.user.verify-account.message.mail-title"))
				.template(DataProcessor.getTemplateByLocale(MailTemplate.CONFIRM_ACCOUNT))
				.props(Map.of(
						"name", user.getGivenName(),
						"password", user.getRawPassword(),
						"verifyLink", createEmailVerifyLink(user.getId())
				))
				.build();
		mailService.send(email);
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

	private String createEmailVerifyLink(Long userId) {
		try {
			String input = DefaultInstance.OBJECT_MAPPER.writeValueAsString(userId);
			String verifyCode = Base64.getEncoder().encodeToString(DataProcessor.encrypt(input, appKey));
			return frontendUrl + ExternalLink.VERIFY_EMAIL_LINK + URLEncoder.encode(verifyCode, StandardCharsets.UTF_8);
		}
		catch (GeneralSecurityException | IOException e) {
			log.error("Cannot create verify code", e);
			throw new UserVerifyCodeException.Creation();
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void register(UserRegisterRequestDto request) {
		User user = mapper.map(request, User.class);
		user.setGroupIds(Set.of(GroupEntity.Type.USER.getId()));
		user.setLocked(EntityStatus.LOCKED);
		createUserInternal(user);
		sendNotify(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void verifyEmail(String code) {
		final Long userId = getUserIdFromVerifyCode(code);
		userRepo.findById(userId).ifPresent(user -> {
			if (Objects.equals(user.getEmailVerified(), UserEntity.EmailVerify.NOT_VERIFIED.value())) {
				user.setEmailVerified(UserEntity.EmailVerify.VERIFIED.value());
				userRepo.save(user);
			}
			else throw new UserVerifyCodeException.Verified();
		});
	}

	private Long getUserIdFromVerifyCode(String code) {
		try {
			byte[] rawData = DataProcessor.decrypt(Base64.getDecoder().decode(code), appKey);
			return DefaultInstance.OBJECT_MAPPER.readValue(rawData, Long.class);
		}
		catch (Exception e) {
			log.error("Cannot decode info from verify code", e);
			throw new UserVerifyCodeException.Invalid();
		}
	}
}
