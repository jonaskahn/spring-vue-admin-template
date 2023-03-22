package io.github.tuyendev.msv.common.dto.token;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class RenewTokenRequestDto implements Serializable {

    @NotNull(message = "{app.auth.validation.refresh-token.required}")
    private String refreshToken;
}
