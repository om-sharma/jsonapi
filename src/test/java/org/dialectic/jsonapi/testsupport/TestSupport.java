package org.dialectic.jsonapi.testsupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestSupport {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
    }

    public static void assertJsonEquals(String expectedStr, String actualStr) {
        assertJsonEquals("AssertionError", expectedStr, actualStr);
    }

    @SneakyThrows
    public static void assertJsonEquals(String message, String expectedStr, String actualStr) {
        assertEquals(
                message + "\n",
                objectMapper.writeValueAsString(objectMapper.readValue(expectedStr, Object.class)),
                objectMapper.writeValueAsString(objectMapper.readValue(actualStr, Object.class))
        );
    }

    @SneakyThrows
    public static JsonNode toJsonNode(Object object) {
        return objectMapper.readTree(objectMapper.writeValueAsBytes(object));
    }

    @SneakyThrows
    public static Map toMap(Object object) {
        return objectMapper.readValue(objectMapper.writeValueAsBytes(object), Map.class);
    }

    public static String toJsonString(Object object) {
        return toJsonNode(object).toString();
    }

    public static String jString(String jsonWithSingleQuotes) {
        return jsonWithSingleQuotes.replaceAll("'", "\"");
    }
}
