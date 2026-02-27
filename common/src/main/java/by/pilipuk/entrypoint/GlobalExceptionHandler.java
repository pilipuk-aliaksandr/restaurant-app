package by.pilipuk.entrypoint;

import by.pilipuk.business.mapper.CustomExceptionMapper;
import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.model.dto.CustomExceptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//TODO ПОЗОР
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final CustomExceptionMapper exceptionMapper;

    @ExceptionHandler(BaseApplicationException.class)
    public CustomExceptionDto handleBaseApplicationException(BaseApplicationException ex) {

        //TODO вынеси в маппер
        var customExceptionDto = exceptionMapper.toCustomExceptionDto(ex);
        //TODO логирование в зависимости от уровня в ex (смотри как сделано в старом проекта)
        String message = String.format("[REPOSITORY]: %s.%s",
                ex.getCode(),
                ex.getMessage());

        switch (ex.getLogLevel()) {
            case ERROR -> log.error(message, ex);
            case WARN -> log.warn(message);
            case DEBUG -> log.debug(message);
            case TRACE -> log.trace(message);
            default -> log.info(message);
        }

        return customExceptionDto;
    }
}