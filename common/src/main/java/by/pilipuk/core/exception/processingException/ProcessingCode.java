package by.pilipuk.core.exception.processingException;

import lombok.Getter;
import org.slf4j.event.Level;

@Getter
public enum ProcessingCode {
    ERROR_OUTBOX_PROCESSING(Level.INFO, ", id: ");

    private final Level level;
    private final String key;

    ProcessingCode(Level level, String key) {
        this.level = level;
        this.key = key;
    }
}
