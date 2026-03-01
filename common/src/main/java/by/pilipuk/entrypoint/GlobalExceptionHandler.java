package by.pilipuk.entrypoint;

import by.pilipuk.business.mapper.ApplicationExceptionMapper;
import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.core.exception.base.OutboxException;
import by.pilipuk.model.dto.ApplicationExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//TODO ПОЗОР
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ApplicationExceptionMapper exceptionMapper;

    @ExceptionHandler(BaseApplicationException.class)
    public ResponseEntity<ApplicationExceptionDto> handleBaseApplicationException(BaseApplicationException ex, HttpServletRequest request) {

        //TODO логирование в зависимости от уровня в ex (смотри как сделано в старом проекта)

        switch (ex.getLogLevel()) {
            case ERROR -> log.error("[ERROR] {}", ex.getMessage(), ex);
            case DEBUG -> log.debug("[DEBUG] {}", ex.getMessage());
            default -> log.info("[INFO] {}", ex.getMessage());
        }

        HttpStatus status = (ex instanceof OutboxException)
                ? HttpStatus.INTERNAL_SERVER_ERROR
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(status)
                .body(exceptionMapper.toDto(ex, request, status.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationExceptionDto> handleOtherApplicationException(Exception ex, HttpServletRequest request) {
        log.error("[ERROR] {}", ex.getMessage(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApplicationExceptionDto applicationExceptionDto = exceptionMapper.toDto(ex, request, status.value(), "INTERNAL_SERVER_ERROR");

        return new ResponseEntity<>(applicationExceptionDto, status);
    }
}