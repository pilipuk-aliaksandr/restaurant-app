package by.pilipuk.exception.base;

import lombok.Getter;
import org.slf4j.event.Level;

@Getter
public abstract class BaseApplicationException extends RuntimeException {
    private final String code;
    private final Level logLevel;

    protected BaseApplicationException(String message, String code, Level logLevel) {
        super(message);
        this.code = code;
        this.logLevel = logLevel;
    }

}