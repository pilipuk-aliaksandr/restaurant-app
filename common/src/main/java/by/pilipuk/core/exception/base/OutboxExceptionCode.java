package by.pilipuk.core.exception.base;

import lombok.Getter;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

@Getter
public enum OutboxExceptionCode {
    FAILED_MESSAGING_TO_KAFKA(Level.INFO, ", id: ");

    private final Level level;
    private final String key;

    OutboxExceptionCode(Level level, String key) {
        this.level = level;
        this.key = key;
    }
}