package by.pilipuk.environment.service;

import by.pilipuk.business.security.UserDetailsDto;
import by.pilipuk.dto.AuthRequest;
import by.pilipuk.dto.AuthResponse;
import by.pilipuk.dto.UserWriteDto;
import by.pilipuk.environment.data.dtosCreation.AuthDtoCreator;
import by.pilipuk.environment.data.dtosCreation.UserDtoCreator;
import by.pilipuk.environment.data.entityCreation.UserCreator;
import by.pilipuk.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTestService {

    private final UserCreator userCreator;
    private final UserDtoCreator userDtoCreator;
    private final AuthDtoCreator authDtoCreator;

    public UserWriteDto createUserWriteDto() {
        return userDtoCreator.createUserWriteDto();
    }

    public User createUser() {
        return userCreator.createUser();
    }

    public User saveUser() {
        return userCreator.saveUser(userCreator.createUser());
    }

    public UserDetailsDto createUserDetailsDto() {
        return userDtoCreator.createUserDetailsDto(createUser());
    }

    public AuthRequest createAuthRequest() {
        return authDtoCreator.createAuthRequest();
    }

    public AuthResponse createAuthResponse() {
        return authDtoCreator.createAuthResponse(createUserDetailsDto());
    }
}