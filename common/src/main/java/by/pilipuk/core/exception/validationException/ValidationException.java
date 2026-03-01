package by.pilipuk.core.exception.validationException;

import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.core.exception.base.BaseValidationException;
import by.pilipuk.model.dto.ExceptionContext;
import org.slf4j.event.Level;

import java.util.List;

public class ValidationException extends BaseValidationException {

    private ValidationException(Level logLevel, ExceptionContext exceptionContext) {
        super(logLevel, exceptionContext);
    }

    public static ValidationException create(ValidationCode code) {
        return new ValidationException(
                code.getLevel(),
                ExceptionContext.create(code.name()));
    }

    public static ValidationException create(Level logLevel, ExceptionContext exceptionContext) {
        return new ValidationException(logLevel, exceptionContext);
    }

    public static ValidationException create(ValidationCode code, Object param) {
        return new ValidationException(
                code.getLevel(),
                ExceptionContext.create(code.name()).add(code.getKey(), param)
        );
    }

    public static ValidationException create(ValidationCode code, List<String> params) {
        return new ValidationException(
                code.getLevel(),
                ExceptionContext.create(code.name()).add(code.getKey(), String.join(", ", params))
        );
    }
}