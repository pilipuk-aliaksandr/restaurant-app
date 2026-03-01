package by.pilipuk.model.dto;

import java.time.Instant;
import java.util.List;

public record CustomExceptionDto(
        int status,
        String url,
        Instant timestamp,
        List<ExceptionContext> details
) {
}