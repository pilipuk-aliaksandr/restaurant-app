package by.pilipuk.controller;

import by.pilipuk.dto.CustomExceptionDto;
import by.pilipuk.exception.base.BaseApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

//TODO ПОЗОР
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseApplicationException.class)
    public ResponseEntity<CustomExceptionDto> handleBaseApplicationException(BaseApplicationException ex) {

        //TODO вынеси в маппер
        CustomExceptionDto customExceptionDto = CustomExceptionDto.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .filterParams(ex.getFilterParams())
                .time(LocalDateTime.now())
                .build();

        //TODO логирование в зависимости от уровня в ex (смотри как сделано в старом проекта)
        log.info("[Repository]: {}.{}",
                ex.getCode(),
                ex.getMessage());

        return new ResponseEntity<>(customExceptionDto, HttpStatus.NOT_FOUND);
    }
}