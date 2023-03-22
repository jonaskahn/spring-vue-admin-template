package io.github.tuyendev.msv.core.application.dto.user;

import com.naharoo.commons.mapstruct.UnidirectionalMapper;
import io.github.tuyendev.msv.common.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRegisterRequestDtoToUserEntityMapper extends UnidirectionalMapper<UserRegisterRequestDto, User>, UserRequestDtoBaseMapper {

    @Mapping(target = "gender", source = "gender", qualifiedByName = "convertGenderEnumToValue")
    @Mapping(target = "username", source = "username", qualifiedByName = "convertToLowerCase")
    @Mapping(target = "email", source = "email", qualifiedByName = "convertToLowerCase")
    User map(UserRegisterRequestDto source);
}
