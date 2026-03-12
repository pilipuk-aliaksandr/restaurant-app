package by.pilipuk.gateway.business.mapper;

import by.pilipuk.gateway.core.security.JwtTokenProvider;
import by.pilipuk.gateway.dto.AuthResponse;
import by.pilipuk.gateway.model.dto.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AuthMapper {

    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    public abstract AuthResponse toAuthResponse(UserDetailsDto userDetailsDto);

    @AfterMapping
    protected void mapTokens(UserDetailsDto dto, @MappingTarget AuthResponse response) {
        response.setAccessToken(jwtTokenProvider.generateAccessToken(dto));
        response.setRefreshToken(jwtTokenProvider.generateRefreshToken(dto));
    }
}
