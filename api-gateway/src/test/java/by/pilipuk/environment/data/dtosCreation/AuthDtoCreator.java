package by.pilipuk.environment.data.dtosCreation;

import by.pilipuk.business.security.JwtTokenProvider;
import by.pilipuk.business.security.UserDetailsDto;
import by.pilipuk.dto.AuthRequest;
import by.pilipuk.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthDtoCreator {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthRequest createAuthRequest() {
        var authRequest = new AuthRequest();
        authRequest.setUsername("Alex");
        authRequest.setPassword("Vishn");

        return authRequest;
    }

    public AuthResponse createAuthResponse(UserDetailsDto userDetailsDto) {
        var authResponse = new AuthResponse();
        authResponse.setId(userDetailsDto.getId());
        authResponse.setUsername(userDetailsDto.getUsername());
        authResponse.setAccessToken(jwtTokenProvider.generateAccessToken(userDetailsDto));
        authResponse.setRefreshToken(jwtTokenProvider.generateRefreshToken(userDetailsDto));

        return authResponse;
    }
}
