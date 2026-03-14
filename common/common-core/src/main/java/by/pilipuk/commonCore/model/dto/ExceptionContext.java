package by.pilipuk.commonCore.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@FieldNameConstants
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = { "code", "field", "message", "expected", "actual", "object", "parameters" })
public class ExceptionContext {

    private String code;
    private String message;
    private String field;
    private Object actual;
    private Object expected;
    private String object;
    private Map<String, Object> parameters;

    private ExceptionContext(String code) {
        this.code = code;
    }

    public static ExceptionContext create(String code) {
        return new ExceptionContext(code);
    }

    public ExceptionContext add(
        String key,
        Object value
    ) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }

        parameters.put(key, value);
        return this;
    }
}