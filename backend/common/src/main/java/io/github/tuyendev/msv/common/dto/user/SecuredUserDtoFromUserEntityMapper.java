package io.github.tuyendev.msv.common.dto.user;

import com.naharoo.commons.mapstruct.UnidirectionalMapper;
import io.github.tuyendev.msv.common.entity.Authority;
import io.github.tuyendev.msv.common.entity.User;
import one.util.streamex.StreamEx;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SecuredUserDtoFromUserEntityMapper extends UnidirectionalMapper<User, SecuredUserDto> {

    @Named(value = "extractAuthorityNames")
    static Set<String> extractAuthorityNames(Set<Authority> authorities) {
        return StreamEx.of(authorities)
                .map(Authority::getName)
                .toImmutableSet();
    }

    @Override
    @Mapping(source = "authorities", target = "authorityNames", qualifiedByName = "extractAuthorityNames")
    SecuredUserDto map(User user);
}
