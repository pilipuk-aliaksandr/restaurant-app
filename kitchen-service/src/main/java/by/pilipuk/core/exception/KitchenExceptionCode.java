package by.pilipuk.core.exception;

import lombok.Getter;
import org.slf4j.event.Level;

@Getter
public enum KitchenExceptionCode {
    NOT_FOUND_BY_ID(Level.INFO, ", id: "),
    NOT_FOUND_BY_FILTER(Level.INFO, ", params: ");

    private final Level level;
    private final String key;

    KitchenExceptionCode(Level level, String key) {
        this.level = level;
        this.key = key;
    }
}