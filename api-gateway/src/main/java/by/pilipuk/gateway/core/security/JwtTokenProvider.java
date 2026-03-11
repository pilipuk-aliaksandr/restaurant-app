package by.pilipuk.gateway.core.security;

import by.pilipuk.gateway.business.service.UserDetailsServiceImpl;
import by.pilipuk.gateway.dto.AuthResponse;
import by.pilipuk.gateway.business.mapper.UserMapper;
import by.pilipuk.gateway.business.repository.UserRepository;
import by.pilipuk.gateway.model.dto.UserDetailsDto;
import by.pilipuk.gateway.model.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserDetailsDto userDetailsDto) {
        Claims claims = Jwts.claims()
                .subject(userDetailsDto.getUsername())
                .add("id", userDetailsDto.getId())
                .add("roles", userDetailsDto.getAuthorities())
                .build();

        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(UserDetailsDto userDetailsDto) {
        Claims claims = Jwts.claims()
                .subject(userDetailsDto.getUsername())
                .add("id", userDetailsDto.getId())
                .build();

        Instant validity = Instant.now()
                .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public AuthResponse refreshUserTokens(String refreshToken) {
        AuthResponse authResponse = new AuthResponse();
        if (!isValid(refreshToken)) {
            throw new AccessDeniedException("Token is not Valid");
        }
        Long userId = Long.valueOf(getId(refreshToken));

        UserDetailsDto userDetailsDto = userMapper.toUserDetailsDto(userRepository.findByIdOrThrow(userId));

        authResponse.setId(userId);
        authResponse.setUsername(userDetailsDto.getUsername());
        authResponse.setAccessToken(generateAccessToken(userDetailsDto));
        authResponse.setRefreshToken(generateRefreshToken(userDetailsDto));
        return authResponse;
    }

    public boolean isValid(String token) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload()
                .getExpiration()
                .after(new Date());
    }

    private String getId(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", String.class);
    }

    private String getUsername(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
    }
}