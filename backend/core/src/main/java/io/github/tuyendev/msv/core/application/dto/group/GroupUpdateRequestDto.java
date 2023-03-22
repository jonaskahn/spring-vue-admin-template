package io.github.tuyendev.msv.core.application.dto.group;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupUpdateRequestDto implements Serializable {


    @NotNull
    private Long id;

    private String name;

    private String description;

    private Integer status;
}
