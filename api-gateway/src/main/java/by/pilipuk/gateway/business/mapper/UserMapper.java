package by.pilipuk.gateway.business.mapper;

import by.pilipuk.gateway.business.repository.UserRoleRepository;
import by.pilipuk.gateway.dto.UserWriteDto;
import by.pilipuk.gateway.model.entity.User;
import by.pilipuk.gateway.model.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected UserRoleRepository userRoleRepository;

    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "userRole", source = "userWriteDto", qualifiedByName = "linkDefaultRole")
    public abstract User toUser(UserWriteDto userWriteDto);

    @Named("encodePassword")
    protected String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("linkDefaultRole")
    protected UserRole linkDefaultRole(UserWriteDto dto) {
        return userRoleRepository.findById(1L).orElseThrow();
    }
}