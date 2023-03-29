package io.github.tuyendev.msv.core.application.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserVerifyRequestDto {

	@NotNull
	private String code;
}
