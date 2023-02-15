package io.github.tuyendev.msv.common.dto.token;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequestDto implements Serializable {

	@NotNull(message = "{app.auth.validation.username.required}")
	private String username;

	@NotNull(message = "{app.auth.validation.password.required}")
	private String password;

	private boolean rememberMe;
}
