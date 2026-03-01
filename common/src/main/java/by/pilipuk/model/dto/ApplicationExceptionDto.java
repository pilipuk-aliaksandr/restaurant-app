package by.pilipuk.model.dto;

import java.time.Instant;
import java.util.List;

public record ApplicationExceptionDto(
        int status,
        String url,
        Instant timestamp,
        List<ExceptionContext> details
) {
}