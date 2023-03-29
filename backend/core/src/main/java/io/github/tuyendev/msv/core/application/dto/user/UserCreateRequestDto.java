package io.github.tuyendev.msv.core.application.dto.user;

import java.util.List;

import io.github.tuyendev.msv.common.constant.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateRequestDto {

	@NotNull
	private String username;

	@NotNull
	private String email;

	private UserEntity.Gender gender;

	private UserEntity.EmailVerify emailVerified;

	private String familyName;

	private String middleName;

	private String givenName;

	private String phoneNumber;

	@NotNull
	private List<Long> groupIds;
}
