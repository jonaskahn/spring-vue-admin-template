package io.github.tuyendev.msv.core.application.dto.user;

import java.util.Objects;

import com.naharoo.commons.mapstruct.UnidirectionalMapper;
import io.github.tuyendev.msv.common.constant.UserEntity;
import io.github.tuyendev.msv.common.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserRequestDtoToUserEntityMapper extends UnidirectionalMapper<UserCreateRequestDto, User> {

	@Named(value = "convertGenderEnumToValue")
	static Integer convertGenderEnumToValue(UserEntity.Gender gender) {
		return Objects.nonNull(gender) ? gender.value() : null;
	}

	@Named(value = "convertEmailVerifyEnumToValue")
	static Integer convertEmailVerifyEnumToValue(UserEntity.EmailVerify verified) {
		return Objects.nonNull(verified) ? verified.value() : null;
	}

	@Named(value = "convertPhoneVerifyEnumToValue")
	static Integer convertPhoneVerifyEnumToValue(UserEntity.PhoneVerify verified) {
		return Objects.nonNull(verified) ? verified.value() : null;
	}

	@Mapping(target = "gender", source = "gender", qualifiedByName = "convertGenderEnumToValue")
	@Mapping(target = "emailVerified", source = "emailVerified", qualifiedByName = "convertEmailVerifyEnumToValue")
	@Mapping(target = "phoneNumberVerified", source = "phoneVerified", qualifiedByName = "convertPhoneVerifyEnumToValue")
	User map(UserCreateRequestDto source);
}
