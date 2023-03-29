package io.github.tuyendev.msv.core.application.api;

import io.github.tuyendev.msv.common.constant.AuthorityType;
import io.github.tuyendev.msv.common.dto.Response;
import io.github.tuyendev.msv.core.application.dto.group.GroupCreateRequestDto;
import io.github.tuyendev.msv.core.application.dto.group.GroupUpdateRequestDto;
import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/group")
public class GroupController {

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('" + AuthorityType.ADMIN_VALUE + "')")
	public Response create(@Valid @RequestBody GroupCreateRequestDto request) {
		return Response.ok();
	}

	@PostMapping("/update")
	@PreAuthorize("hasAnyAuthority('" + AuthorityType.ADMIN_VALUE + "', '" + AuthorityType.EDITOR_VALUE + "')")
	public Response update(@Valid @RequestBody GroupUpdateRequestDto request) {
		return Response.ok();
	}

	@GetMapping("/view")
	public Response list() {
		return Response.ok();
	}

}
