package by.pilipuk.core.exception.processingException;

import lombok.Getter;
import org.slf4j.event.Level;
import static org.slf4j.event.Level.ERROR;

@Getter
public enum ProcessingCode {
    FAILED_PROCESSING(ERROR);

    private final Level level;

    ProcessingCode(Level level) {
        this.level = level;
    }
}