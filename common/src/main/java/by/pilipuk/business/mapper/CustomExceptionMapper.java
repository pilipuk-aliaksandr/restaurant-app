package by.pilipuk.business.mapper;


import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.model.dto.CustomExceptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(
        componentModel = "spring",
        imports = {LocalDateTime.class}
)
public abstract class CustomExceptionMapper {

    @Mapping(target = "code", source = "code")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "filterParams", source = "filterParams")
    @Mapping(target = "time", expression = "java(LocalDateTime.now())")
    public abstract CustomExceptionDto toCustomExceptionDto(BaseApplicationException ex);
}
