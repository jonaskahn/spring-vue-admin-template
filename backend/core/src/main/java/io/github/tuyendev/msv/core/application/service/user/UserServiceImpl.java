package io.github.tuyendev.msv.core.application.service.user;

import com.naharoo.commons.mapstruct.MappingFacade;
import io.github.tuyendev.msv.common.entity.User;
import io.github.tuyendev.msv.common.repository.UserRepository;
import io.github.tuyendev.msv.core.application.dto.user.UserCreateRequestDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

	private final MappingFacade mapper;

	private final UserRepository userRepo;

	@Override
	public void create(UserCreateRequestDto request) {
		User user = mapper.map(request, User.class);
		userRepo.save(user);
	}
}
