package by.pilipuk.core.exception;

import lombok.Getter;
import org.slf4j.event.Level;

@Getter
public enum ApplicationExceptionCode {
    FAILED_MESSAGING_TO_KAFKA(Level.INFO, ", id: ");

    private final Level level;
    private final String key;

    ApplicationExceptionCode(Level level, String key) {
        this.level = level;
        this.key = key;
    }
}