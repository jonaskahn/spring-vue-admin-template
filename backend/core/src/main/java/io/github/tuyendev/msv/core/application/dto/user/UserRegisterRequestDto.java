package io.github.tuyendev.msv.core.application.dto.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.tuyendev.msv.common.constant.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterRequestDto {
	@NotNull
	private String username;

	@NotNull
	private String email;

	private UserEntity.Gender gender;

	@NotNull
	private String familyName;

	private String middleName;

	@NotNull
	private String givenName;

	private String phoneNumber;

	@NotNull
	@JsonProperty(value = "password")
	private String rawPassword;
}
