package by.pilipuk.entrypoint.controller;

import by.pilipuk.api.AuthApi;
import by.pilipuk.dto.AuthRequest;
import by.pilipuk.dto.AuthResponse;
import by.pilipuk.dto.UserWriteDto;
import by.pilipuk.business.service.AuthService;
import by.pilipuk.business.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userService;
    private final AuthService authService;

    @Override
    public AuthResponse authenticateCurrentUser(AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @Override
    public void registerNewUser(UserWriteDto userWriteDto) {
        userService.saveUser(userWriteDto);
    }
}