package io.github.tuyendev.msv.common.dto.token;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenInfoDto implements Serializable {

	private boolean valid;

	private String issuer;

	private String type;

	private boolean revoked;

	private boolean expired;
}
