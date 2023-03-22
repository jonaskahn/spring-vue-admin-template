package io.github.tuyendev.msv.common.dto.token;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TokenInfoDto implements Serializable {

    private boolean valid;

    private String issuer;

    private String type;

    private boolean revoked;

    private boolean expired;
}
