package by.pilipuk.exception;

import lombok.Getter;
import org.slf4j.event.Level;

@Getter
public enum ValidationCode {
    NOT_FOUND_BY_ID(Level.INFO, ", id: ");

    private final Level level;
    private final String key;

    ValidationCode(Level level, String key) {
        this.level = level;
        this.key = key;
    }

}