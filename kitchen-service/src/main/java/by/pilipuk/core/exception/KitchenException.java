package by.pilipuk.core.exception;

import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.model.dto.ExceptionContext;
import org.slf4j.event.Level;

public class KitchenException extends BaseApplicationException {

    private static final String CODE = "KITCHEN_APPLICATION_EXCEPTION";

    private KitchenException(Level logLevel, ExceptionContext exceptionContext) {
        super(CODE, logLevel, exceptionContext, null);
    }

    public static KitchenException create(KitchenExceptionCode code, Object param) {
        return new KitchenException(
                code.getLevel(),
                ExceptionContext.create(code.name()).add(code.getKey(), param)
        );
    }
}