package io.github.tuyendev.msv.common.dto.token;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RenewTokenRequestDto implements Serializable {

	@NotNull(message = "{app.auth.validation.refresh-token.required}")
	private String refreshToken;
}
