package by.pilipuk.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CustomExceptionDto {
    private String code;
    private String message;
    private LocalDateTime time;
}
