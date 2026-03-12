package by.pilipuk.gateway.core.security;

import by.pilipuk.gateway.business.mapper.AuthMapper;
import by.pilipuk.gateway.business.mapper.UserDetailsMapper;
import by.pilipuk.gateway.business.service.UserDetailsServiceImpl;
import by.pilipuk.gateway.dto.AuthResponse;
import by.pilipuk.gateway.business.repository.UserRepository;
import by.pilipuk.gateway.model.dto.UserDetailsDto;
import by.pilipuk.gateway.model.prop.JwtProperties;
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
    private final UserDetailsMapper userDetailsMapper;
    private SecretKey key;

    @PostConstruct
    public void init() {
        var keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserDetailsDto userDetailsDto) {
        var claims = Jwts.claims()
                .subject(userDetailsDto.getUsername())
                .add("id", userDetailsDto.getId())
                .add("roles", userDetailsDto.getAuthorities())
                .build();

        var validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(UserDetailsDto userDetailsDto) {
        var claims = Jwts.claims()
                .subject(userDetailsDto.getUsername())
                .add("id", userDetailsDto.getId())
                .build();

        var validity = Instant.now()
                .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public AuthResponse refreshUserTokens(String refreshToken) {
        if (!isValid(refreshToken)) {
            throw new AccessDeniedException("Token is not Valid");
        }
        var userId = Long.valueOf(getId(refreshToken));

        var userDetailsDto = userDetailsMapper.toUserDetailsDto(userRepository.findByIdOrElseThrow(userId));

        var response = new AuthResponse();

        response.setId(userDetailsDto.getId());
        response.setUsername(userDetailsDto.getUsername());
        response.setAccessToken(generateAccessToken(userDetailsDto));
        response.setRefreshToken(generateRefreshToken(userDetailsDto));

        return response;
    }

    public boolean isValid(String token) {
        var claims = Jwts
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
        var username = getUsername(token);
        var userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}