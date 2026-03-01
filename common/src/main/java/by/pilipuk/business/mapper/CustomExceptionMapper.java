package by.pilipuk.business.mapper;

import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.model.dto.CustomExceptionDto;
import by.pilipuk.model.dto.ExceptionContext;
import jakarta.servlet.http.HttpServletRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.time.Instant;
import java.util.List;

@Mapper(
        componentModel = "spring",
        imports = {Instant.class}
)
public abstract class CustomExceptionMapper {

    @Mapping(target = "status", source = "status")
    @Mapping(target = "url", source = "request.requestURI")
    @Mapping(target = "timestamp", expression = "java(Instant.now())")
    @Mapping(target = "details", expression = "java(java.util.List.of(ex.getExceptionContext()))")
    public abstract CustomExceptionDto toDto(BaseApplicationException ex, HttpServletRequest request, int status);

    public CustomExceptionDto toDto(Exception ex, HttpServletRequest request, int status, String code) {
        ExceptionContext context = ExceptionContext.create(code);
        context.setMessage(ex.getMessage());

        return new CustomExceptionDto(status, request.getRequestURI(), Instant.now(), List.of(context));
    }
}