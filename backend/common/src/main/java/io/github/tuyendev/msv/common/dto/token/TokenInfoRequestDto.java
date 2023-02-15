package io.github.tuyendev.msv.common.dto.token;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenInfoRequestDto implements Serializable {

	@NotNull(message = "{app.auth.validation.token-info.required}")
	private String token;
}
