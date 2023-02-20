package io.github.tuyendev.msv.core.application.dto.user;

import java.util.Objects;

import io.github.tuyendev.msv.common.constant.UserEntity;
import org.mapstruct.Named;

public interface UserRequestDtoBaseMapper {

	@Named(value = "convertGenderEnumToValue")
	static Integer convertGenderEnumToValue(UserEntity.Gender gender) {
		return Objects.nonNull(gender) ? gender.value() : UserEntity.Gender.UNKNOWN.value();
	}

	@Named(value = "convertEmailVerifyEnumToValue")
	static Integer convertEmailVerifyEnumToValue(UserEntity.EmailVerify verified) {
		return Objects.nonNull(verified) ? verified.value() : UserEntity.EmailVerify.NOT_VERIFIED.value();
	}

	@Named(value = "convertPhoneVerifyEnumToValue")
	static Integer convertPhoneVerifyEnumToValue(UserEntity.PhoneVerify verified) {
		return Objects.nonNull(verified) ? verified.value() : UserEntity.PhoneVerify.NOT_VERIFIED.value();
	}

	@Named(value = "convertToLowerCase")
	static String lowerCase(String s) {
		return Objects.nonNull(s) ? s.toLowerCase() : null;
	}

}
