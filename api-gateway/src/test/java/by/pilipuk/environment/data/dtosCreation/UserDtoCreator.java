package by.pilipuk.environment.data.dtosCreation;

import by.pilipuk.business.mapper.UserMapper;
import by.pilipuk.business.security.UserDetailsDto;
import by.pilipuk.dto.UserWriteDto;
import by.pilipuk.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoCreator {

    private final UserMapper userMapper;

    public UserWriteDto createUserWriteDto() {
        var userWriteDto = new UserWriteDto();
        userWriteDto.setUsername("Alex");
        userWriteDto.setPassword("Vishn");

        return userWriteDto;
    }

    public UserDetailsDto createUserDetailsDto(User user) {
        return userMapper.toUserDetailsDto(user);
    }
}
