package by.pilipuk.core.exception.base;

import lombok.Getter;
import org.slf4j.event.Level;

//в коммон
//globalexception handler туда же
@Getter
public abstract class BaseApplicationException extends RuntimeException {
    private final String code;
    private final Level logLevel;
    private final String filterParams;

    protected BaseApplicationException(String message, String filterParams, String code, Level logLevel) {
        super(message);
        this.filterParams = filterParams;
        this.code = code;
        this.logLevel = logLevel;
    }

}