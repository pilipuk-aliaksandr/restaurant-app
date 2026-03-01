package by.pilipuk.core.exception.base;

import by.pilipuk.model.dto.ExceptionContext;
import lombok.Getter;
import org.slf4j.event.Level;

import static java.util.Optional.ofNullable;

//в коммон
//globalexception handler туда же
@Getter
public abstract class BaseApplicationException extends RuntimeException {
    private final String code;
    private final Level logLevel;
    private final ExceptionContext exceptionContext;

    private static final String parametrizedMessage = "Code: %s, params: [%s]";
    private static final String message = "Code: %s";

    protected BaseApplicationException(String code, Level logLevel, ExceptionContext exceptionContext, Throwable cause) {
        super(createSuperMessage(exceptionContext), cause);
        this.exceptionContext = exceptionContext;
        this.code = code;
        this.logLevel = logLevel;
    }

    private static String createSuperMessage(ExceptionContext exceptionContext) {
        return ofNullable(exceptionContext.getParameters())
                .map(parameters -> parametrizedMessage.formatted(exceptionContext.getCode(), parameters))
                .orElse(message.formatted(exceptionContext.getCode()));
    }
}