package io.github.tuyendev.msv.core.application.dto.group;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupCreateRequestDto implements Serializable {

    @NotNull
    private String name;

    private String description;
}
