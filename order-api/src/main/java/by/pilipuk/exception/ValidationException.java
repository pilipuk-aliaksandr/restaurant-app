package by.pilipuk.exception;

import by.pilipuk.exception.base.BaseApplicationException;

public class ValidationException extends BaseApplicationException {

    private static final String CODE = "APPLICATION_VALIDATION_EXCEPTION";

    private ValidationException(ValidationCode code, Long id) {
        super(code.name() + code.getKey() + id, CODE, code.getLevel());
    }

    public static ValidationException create(ValidationCode code, Long id) {
        return new ValidationException(code, id);
    }

}