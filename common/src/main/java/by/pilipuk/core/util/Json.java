package by.pilipuk.core.util;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class Json {

    private static final String CODE = "OBJECT_MAPPER_EXCEPTION";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String serialize(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static <T> T deserialize(
            String object,
            Class<T> objectType
    ) {
        try {
            return MAPPER.readValue(object, objectType);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static <T> T deserialize(
            String object,
            TypeReference<T> objectType
    ) {
        try {
            return MAPPER.readValue(object, objectType);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static ObjectMapper mapper() {
        return MAPPER;
    }
}