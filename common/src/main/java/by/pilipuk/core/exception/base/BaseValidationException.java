package by.pilipuk.core.exception.base;

import by.pilipuk.model.dto.ExceptionContext;
import org.slf4j.event.Level;

public class BaseValidationException extends BaseApplicationException {
    private static final String CODE = "APPLICATION_VALIDATION_EXCEPTION";

    protected BaseValidationException(Level logLevel, ExceptionContext exceptionContext) {
        super(CODE, logLevel, exceptionContext, null);
    }
}