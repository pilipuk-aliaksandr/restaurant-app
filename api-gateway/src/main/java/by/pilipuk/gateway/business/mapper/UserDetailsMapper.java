package by.pilipuk.gateway.business.mapper;

import by.pilipuk.gateway.model.dto.UserDetailsDto;
import by.pilipuk.gateway.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserDetailsMapper {

    @Mapping(target = "authorities", source = ".", qualifiedByName = "mapAuthorities")
    public abstract UserDetailsDto toUserDetailsDto(User user);

    @Named("mapAuthorities")
    protected List<SimpleGrantedAuthority> mapAuthorities(User user) {
        return List.of(new SimpleGrantedAuthority(user.getUserRole().getRole().name()));
    }
}