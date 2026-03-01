package by.pilipuk.exception;

import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.model.dto.ExceptionContext;
import org.slf4j.event.Level;

public class OrderException extends BaseApplicationException {

    private static final String CODE = "ORDER_APPLICATION_EXCEPTION";

    private OrderException(Level logLevel, ExceptionContext exceptionContext) {
        super(CODE, logLevel, exceptionContext, null);
    }

    public static OrderException create(OrderExceptionCode code, Object param) {
        return new OrderException(
                code.getLevel(),
                ExceptionContext.create(code.name()).add(code.getKey(), param)
        );
    }
}