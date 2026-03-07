package by.pilipuk.core.exception;

import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.model.dto.ExceptionContext;
import org.slf4j.event.Level;

public class ApplicationException extends BaseApplicationException {

    private static final String CODE = "GATEWAY_APPLICATION_EXCEPTION";

    private ApplicationException(Level logLevel, ExceptionContext exceptionContext) {
        super(CODE, logLevel, exceptionContext, null);
    }

    public static ApplicationException create(ApplicationExceptionCode code, Object param) {
        return new ApplicationException(
                code.getLevel(),
                ExceptionContext.create(code.name()).add(code.getKey(), param)
        );
    }
}