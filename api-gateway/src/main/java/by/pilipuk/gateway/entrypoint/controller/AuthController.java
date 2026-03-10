package by.pilipuk.gateway.entrypoint.controller;

import by.pilipuk.gateway.api.AuthApi;
import by.pilipuk.gateway.dto.AuthRequest;
import by.pilipuk.gateway.dto.AuthResponse;
import by.pilipuk.gateway.dto.UserWriteDto;
import by.pilipuk.gateway.business.service.AuthService;
import by.pilipuk.gateway.business.service.UserService;
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