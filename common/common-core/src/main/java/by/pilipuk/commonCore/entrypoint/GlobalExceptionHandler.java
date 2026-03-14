package by.pilipuk.commonCore.entrypoint;

import by.pilipuk.commonCore.business.mapper.ApplicationExceptionMapper;
import by.pilipuk.commonCore.core.exception.base.BaseApplicationException;
import by.pilipuk.commonCore.model.dto.ExceptionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ApplicationExceptionMapper exceptionMapper;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseApplicationException.class)
    public ExceptionDto handleBaseApplicationException(BaseApplicationException ex) {

        switch (ex.getLogLevel()) {
            case ERROR -> log.error("[ERROR] {}", ex.getMessage(), ex);
            case DEBUG -> log.debug("[DEBUG] {}", ex.getMessage());
            default -> log.info("[INFO] {}", ex.getMessage());
        }

        return exceptionMapper.toExceptionDto(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionDto handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.debug("MethodArgumentNotValidException occurred:", ex);

        return exceptionMapper.toExceptionDto(ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionDto handleOtherApplicationException(Exception ex) {
        log.error("[ERROR] {}", ex.getMessage(), ex);

        return exceptionMapper.toExceptionDto(ex);
    }
}