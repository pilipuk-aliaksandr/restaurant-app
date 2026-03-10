package by.pilipuk.core.exception;

import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.model.dto.ExceptionContext;
import org.slf4j.event.Level;

public class ApplicationException extends BaseApplicationException {

    private static final String CODE = "COMMON_PROCESSING_EXCEPTION";

    protected ApplicationException(Level logLevel, ExceptionContext exceptionContext, Throwable cause) {
        super(CODE, logLevel, exceptionContext, cause);
    }

    public static ApplicationException create(ApplicationExceptionCode code, Throwable cause) {
        return new ApplicationException(code.getLevel(),
                ExceptionContext.create(code.name()),
                cause);
    }
}
