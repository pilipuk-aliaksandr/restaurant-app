package by.pilipuk.core.exception;


import by.pilipuk.core.exception.base.BaseApplicationException;

public class ValidationException extends BaseApplicationException {

    private static final String CODE = "APPLICATION_VALIDATION_EXCEPTION";

    private ValidationException(ValidationCode code, Long id) {
        super(code.name() + code.getKey() + id, "id: " + id, CODE, code.getLevel());
    }

    private ValidationException(ValidationCode code, String requestParams) {
        super(code.name(), requestParams, CODE, code.getLevel());
    }

    public static ValidationException create(ValidationCode code, Long id) {
        return new ValidationException(code, id);
    }

    public static ValidationException create(ValidationCode code, String requestParams) {
        return new ValidationException(code, requestParams);
    }

}