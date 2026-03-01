package by.pilipuk.core.exception.base;

import by.pilipuk.model.dto.ExceptionContext;
import org.slf4j.event.Level;

public class BaseProcessingException extends BaseApplicationException {
    private static final String CODE = "APPLICATION_PROCESSING_EXCEPTION";

    protected BaseProcessingException(Level logLevel, ExceptionContext exceptionContext, Throwable cause) {
        super(CODE, logLevel, exceptionContext, cause);
    }
}
