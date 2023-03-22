package io.github.tuyendev.msv.common.dto.token;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class TokenInfoRequestDto implements Serializable {

    @NotNull(message = "{app.auth.validation.token-info.required}")
    private String token;
}
