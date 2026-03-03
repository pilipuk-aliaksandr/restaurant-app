package by.pilipuk.business.mapper;

import by.pilipuk.core.exception.base.BaseApplicationException;
import by.pilipuk.core.util.Json;
import by.pilipuk.model.dto.ExceptionContext;
import by.pilipuk.model.dto.ExceptionDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.core.type.TypeReference;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.isNull;

@Mapper(
        componentModel = "spring",
        imports = {Instant.class}
)
public abstract class ApplicationExceptionMapper {

    @Mapping(target = "contexts", expression = "java(java.util.List.of(ex.getExceptionContext()))")
    @Mapping(target = "rootCause", expression = "java(ex.getCause() != null ? ex.getCause().getMessage() : null)")
    public abstract ExceptionDto toExceptionDto(BaseApplicationException ex);

    @Mapping(target = "code", constant = "REQUEST_VALIDATION")
    @Mapping(target = "contexts", source = ".", qualifiedByName = "mapContexts")
    @Mapping(target = "rootCause", source = ".", qualifiedByName = "getRootCause")
    public abstract ExceptionDto toExceptionDto(MethodArgumentNotValidException e);

    @Named("mapContexts")
    protected List<ExceptionContext> mapContexts(MethodArgumentNotValidException e) {
        var contexts = new ArrayList<ExceptionContext>();

        for (var error : e.getBindingResult().getAllErrors()) {
            var message = error.getDefaultMessage();
            if (message == null || message.isBlank()) {
                continue;
            }

            contexts.addAll(deserialize(error, message));
        }

        return contexts;
    }

    @Named("getRootCause")
    protected String getRootCause(Exception e) {
        return ExceptionUtils.getRootCauseMessage(e);
    }

    @Named("mapConstraintViolationContexts")
    protected List<ExceptionContext> mapConstraintViolationContexts(ConstraintViolationException e) {
        var contexts = new ArrayList<ExceptionContext>();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            var message = violation.getMessage();
            if (message == null || message.isBlank()) {
                continue;
            }

            try {
                var violationContexts = Json.deserialize(
                        message, new TypeReference<List<ExceptionContext>>() {
                        }
                );
                violationContexts.forEach(context -> {
                    if (isNull(context.getField()) && violation.getPropertyPath() != null) {
                        context.setField(violation.getPropertyPath().toString());
                    }
                });
                contexts.addAll(violationContexts);
            } catch (Exception ex) {
                var context = ExceptionContext.create(
                                toSnakeCaseCode(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()))
                        .setMessage(message);

                if (violation.getPropertyPath() != null) {
                    context.setField(violation.getPropertyPath().toString());
                }

                if (violation.getInvalidValue() != null) {
                    context.setActual(violation.getInvalidValue());
                }

                contexts.add(context);
            }
        }

        return contexts;
    }

    private List<ExceptionContext> deserialize(ObjectError error, String message) {
        try {
            var contexts = Json.deserialize(
                    message, new TypeReference<List<ExceptionContext>>() {
                    }
            );
            contexts.forEach(context -> fillFields(error, context));
            return contexts;
        } catch (Exception e) {
            var context = ExceptionContext.create(toSnakeCaseCode(error.getCode()))
                    .setMessage(error.getDefaultMessage());

            fillFields(error, context);

            return Collections.singletonList(context);
        }
    }

    private static String toSnakeCaseCode(String code) {
        if (code == null) {
            return null;
        }

        return code
                .replaceAll(CAMEL_CASE_LOWER_TO_UPPER_REGEX, SNAKE_CASE_SEPARATOR_REPLACEMENT)
                .replaceAll(CAMEL_CASE_ACRONYM_REGEX, SNAKE_CASE_SEPARATOR_REPLACEMENT)
                .toUpperCase();
    }

    private static final String CAMEL_CASE_LOWER_TO_UPPER_REGEX = "([a-z0-9])([A-Z])";
    private static final String CAMEL_CASE_ACRONYM_REGEX = "([A-Z]+)([A-Z][a-z])";
    private static final String SNAKE_CASE_SEPARATOR_REPLACEMENT = "$1_$2";

    private static void fillFields(ObjectError objectError, ExceptionContext context) {
        if (objectError instanceof FieldError fieldError) {
            if (isNull(context.getField())) {
                context.setField(fieldError.getField());
            }
            if (isNull(context.getActual())) {
                context.setActual(fieldError.getRejectedValue());
            }
        }

        if (isNull(context.getObject())) {
            context.setObject(objectError.getObjectName());
        }

        if (isNull(context.getExpected()) && objectError.getArguments() != null) {
            Object[] args = objectError.getArguments();
            if (args.length > 1) {
                context.setExpected(args[1]);
            }
        }
    }

    public ExceptionDto toExceptionDto(Exception ex) {
        ExceptionContext context = ExceptionContext.create("INTERNAL_SERVER_ERROR");
        context.setMessage(ex.getMessage());

        return new ExceptionDto("UNEXPECTED_EXCEPTION", List.of(context), ex.getCause().toString());
    }
}