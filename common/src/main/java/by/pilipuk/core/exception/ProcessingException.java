package by.pilipuk.core.exception;

import by.pilipuk.core.exception.base.BaseApplicationException;

public class ProcessingException extends BaseApplicationException {

    private static final String CODE = "APPLICATION_PROCESSING_EXCEPTION";

    private ProcessingException(ProcessingCode code, Long id) {
        super(code.name() + code.getKey() + id, "id: " + id, CODE, code.getLevel());
    }

    public static ProcessingException create(ProcessingCode code, Long id) {
        return new ProcessingException(code, id);
    }


}
