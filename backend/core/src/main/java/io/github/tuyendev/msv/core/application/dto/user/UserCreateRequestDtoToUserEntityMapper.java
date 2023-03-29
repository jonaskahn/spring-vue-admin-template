package io.github.tuyendev.msv.core.application.dto.user;

import com.naharoo.commons.mapstruct.UnidirectionalMapper;
import io.github.tuyendev.msv.common.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCreateRequestDtoToUserEntityMapper extends UnidirectionalMapper<UserCreateRequestDto, User>, UserRequestDtoBaseMapper {

	@Mapping(target = "gender", source = "gender", qualifiedByName = "convertGenderEnumToValue")
	@Mapping(target = "emailVerified", source = "emailVerified", qualifiedByName = "convertEmailVerifyEnumToValue")
	@Mapping(target = "username", source = "username", qualifiedByName = "convertToLowerCase")
	@Mapping(target = "email", source = "email", qualifiedByName = "convertToLowerCase")
	User map(UserCreateRequestDto source);
}
