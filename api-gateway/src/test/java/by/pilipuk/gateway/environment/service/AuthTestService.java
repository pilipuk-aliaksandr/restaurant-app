package by.pilipuk.gateway.environment.service;

import by.pilipuk.gateway.model.dto.UserDetailsDto;
import by.pilipuk.gateway.dto.AuthRequest;
import by.pilipuk.gateway.dto.AuthResponse;
import by.pilipuk.gateway.dto.UserWriteDto;
import by.pilipuk.gateway.environment.data.dtosCreation.AuthDtoCreator;
import by.pilipuk.gateway.environment.data.dtosCreation.UserDtoCreator;
import by.pilipuk.gateway.environment.data.entityCreation.UserCreator;
import by.pilipuk.gateway.model.entity.User;
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