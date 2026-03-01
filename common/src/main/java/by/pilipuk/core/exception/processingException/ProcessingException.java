package by.pilipuk.core.exception.processingException;

import by.pilipuk.core.exception.base.BaseProcessingException;
import by.pilipuk.model.dto.ExceptionContext;
import org.slf4j.event.Level;
import static by.pilipuk.core.exception.processingException.ProcessingCode.FAILED_PROCESSING;

public class ProcessingException extends BaseProcessingException {

    private ProcessingException(Level logLevel, ExceptionContext exceptionContext, Throwable cause) {
        super(logLevel, exceptionContext, cause);
    }

    public static ProcessingException create(Throwable cause) {
        return new ProcessingException(
                FAILED_PROCESSING.getLevel(),
                ExceptionContext.create(FAILED_PROCESSING.name()),
                cause
        );
    }

    public static ProcessingException create(ProcessingCode code, Throwable cause) {
        return new ProcessingException(
                code.getLevel(),
                ExceptionContext.create(code.name()),
                cause
        );
    }

}
