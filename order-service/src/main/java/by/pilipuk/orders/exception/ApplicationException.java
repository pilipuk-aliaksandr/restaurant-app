package by.pilipuk.orders.exception;

import by.pilipuk.commonCore.core.exception.base.BaseApplicationException;
import by.pilipuk.commonCore.model.dto.ExceptionContext;
import org.slf4j.event.Level;

public class ApplicationException extends BaseApplicationException {

    private static final String CODE = "ORDER_APPLICATION_EXCEPTION";

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