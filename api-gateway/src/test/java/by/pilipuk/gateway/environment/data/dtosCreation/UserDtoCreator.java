package by.pilipuk.gateway.environment.data.dtosCreation;

import by.pilipuk.gateway.business.mapper.UserDetailsMapper;
import by.pilipuk.gateway.model.dto.UserDetailsDto;
import by.pilipuk.gateway.dto.UserWriteDto;
import by.pilipuk.gateway.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoCreator {

    private final UserDetailsMapper userDetailsMapper;

    public UserWriteDto createUserWriteDto() {
        var userWriteDto = new UserWriteDto();
        userWriteDto.setUsername("Alex");
        userWriteDto.setPassword("Vishn");

        return userWriteDto;
    }

    public UserDetailsDto createUserDetailsDto(User user) {
        return userDetailsMapper.toUserDetailsDto(user);
    }
}