package by.pilipuk.gateway.business.mapper;

import by.pilipuk.gateway.dto.UserWriteDto;
import by.pilipuk.gateway.model.entity.Role;
import by.pilipuk.gateway.model.entity.User;
import by.pilipuk.gateway.model.entity.UserRole;
import by.pilipuk.gateway.business.security.UserDetailsDto;
import org.mapstruct.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "authorities", source = ".", qualifiedByName = "mapAuthorities")
    public abstract UserDetailsDto toUserDetailsDto(User user);


    @Named("mapAuthorities")
    protected List<SimpleGrantedAuthority> mapAuthorities(User user) {
        return List.of(new SimpleGrantedAuthority(user.getUserRole().getRole().name()));
    }

    @Mapping(target = "userRole", ignore = true)
    public abstract User toUser(UserWriteDto userWriteDto);

    @AfterMapping
    protected void fillUserRole(UserWriteDto dto, @MappingTarget User user) {
        UserRole role = new UserRole();
        role.setRole(Role.ROLE_USER);
        user.setUserRole(role);
    }
}
