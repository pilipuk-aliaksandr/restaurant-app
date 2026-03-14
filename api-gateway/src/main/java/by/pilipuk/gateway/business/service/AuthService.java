package by.pilipuk.gateway.business.service;

import by.pilipuk.gateway.business.mapper.AuthMapper;
import by.pilipuk.gateway.business.mapper.UserDetailsMapper;
import by.pilipuk.gateway.dto.AuthRequest;
import by.pilipuk.gateway.dto.AuthResponse;
import by.pilipuk.gateway.business.repository.UserRepository;
import by.pilipuk.gateway.core.security.JwtTokenProvider;
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
    private final UserDetailsMapper userDetailsMapper;
    private final AuthMapper authMapper;

    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        var user = userRepository.findByUsernameIgnoreCaseOrElseThrow(authRequest.getUsername());

        return authMapper.toAuthResponse(userDetailsMapper.toUserDetailsDto(user));
    }

    public AuthResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}