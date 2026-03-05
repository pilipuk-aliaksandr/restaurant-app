package by.pilipuk.business.service;

import by.pilipuk.dto.AuthRequest;
import by.pilipuk.dto.AuthResponse;
import by.pilipuk.model.entity.User;
import by.pilipuk.business.mapper.UserMapper;
import by.pilipuk.business.repository.UserRepository;
import by.pilipuk.business.security.UserDetailsDto;
import by.pilipuk.business.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    public AuthResponse login(AuthRequest authRequest) {
        AuthResponse authResponse = new AuthResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        User user = userRepository.findByUsernameIgnoreCaseOrElseThrow(authRequest.getUsername());

        UserDetailsDto userDetailsDto = userMapper.toUserDetailsDto(user);

        authResponse.setId(userDetailsDto.getId());
        authResponse.setUsername(userDetailsDto.getUsername());
        authResponse.setAccessToken(jwtTokenProvider.generateAccessToken(userDetailsDto));
        authResponse.setRefreshToken(jwtTokenProvider.generateRefreshToken(userDetailsDto));
        return authResponse;
    }

    public AuthResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}