package io.github.tuyendev.msv.core.application.service.user;

import io.github.tuyendev.msv.core.application.dto.user.UserCreateRequestDto;
import io.github.tuyendev.msv.core.application.dto.user.UserRegisterRequestDto;

public interface UserService extends ViewUserService {

	void create(UserCreateRequestDto request);

	void register(UserRegisterRequestDto request);
}
