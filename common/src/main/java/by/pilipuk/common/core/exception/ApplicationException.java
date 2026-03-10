package by.pilipuk.common.core.exception;

import by.pilipuk.common.core.exception.base.BaseApplicationException;
import by.pilipuk.common.model.dto.ExceptionContext;
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
