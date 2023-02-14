package io.github.tuyendev.msv.common.dto.auth;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthorizationRequestDto implements Serializable {

	@NotNull(message = "{app.auth.validation.required-username}")
	private String username;

	@NotNull(message = "{app.auth.validation.required-password}")
	private String password;

	private boolean rememberMe;
}
