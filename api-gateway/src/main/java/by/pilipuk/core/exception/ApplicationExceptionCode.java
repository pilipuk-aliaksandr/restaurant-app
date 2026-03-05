package by.pilipuk.core.exception;

import lombok.Getter;
import org.slf4j.event.Level;

@Getter
public enum ApplicationExceptionCode {
    NOT_FOUND_BY_ID(Level.INFO, "id"),
    NOT_FOUND_BY_USERNAME(Level.INFO, "username");

    private final Level level;
    private final String key;

    ApplicationExceptionCode(Level level, String key) {
        this.level = level;
        this.key = key;
    }
}