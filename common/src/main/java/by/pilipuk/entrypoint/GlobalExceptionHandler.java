package by.pilipuk.entrypoint;

import by.pilipuk.business.mapper.CustomExceptionMapper;
import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.core.exception.processingException.ProcessingException;
import by.pilipuk.model.dto.CustomExceptionDto;
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

    private final CustomExceptionMapper exceptionMapper;

    @ExceptionHandler(BaseApplicationException.class)
    public ResponseEntity<CustomExceptionDto> handleBaseApplicationException(BaseApplicationException ex, HttpServletRequest request) {

        //TODO логирование в зависимости от уровня в ex (смотри как сделано в старом проекта)

        switch (ex.getLogLevel()) {
            case ERROR -> log.error("[ERROR] {}", ex.getMessage(), ex);
            case DEBUG -> log.debug("[DEBUG] {}", ex.getMessage());
            default -> log.info("[INFO] {}", ex.getMessage());
        }

        HttpStatus status = (ex instanceof ProcessingException)
                ? HttpStatus.INTERNAL_SERVER_ERROR
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(status)
                .body(exceptionMapper.toDto(ex, request, status.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionDto> handleOtherApplicationException(Exception ex, HttpServletRequest request) {
        log.error("[ERROR] {}", ex.getMessage(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        CustomExceptionDto customExceptionDto = exceptionMapper.toDto(ex, request, status.value(), "INTERNAL_SERVER_ERROR");

        return new ResponseEntity<>(customExceptionDto, status);
    }
}