package io.github.tuyendev.msv.common.dto.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class RefreshTokenRequestDto implements Serializable {

	private String refreshToken;
}
