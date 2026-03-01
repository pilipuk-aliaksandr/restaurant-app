package by.pilipuk.core.exception.base;

import by.pilipuk.model.dto.ExceptionContext;
import org.slf4j.event.Level;

public class OutboxException extends BaseApplicationException {

    private static final String CODE = "OUTBOX_PROCESSING_EXCEPTION";

    protected OutboxException(Level logLevel, ExceptionContext exceptionContext, Throwable cause) {
        super(CODE, logLevel, exceptionContext, cause);
    }

    public static OutboxException create(OutboxExceptionCode code, Throwable cause) {
        return new OutboxException(code.getLevel(),
                ExceptionContext.create(code.name()),
                cause);
    }
}
