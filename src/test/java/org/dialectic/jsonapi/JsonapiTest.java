package org.dialectic.jsonapi;

import org.junit.Test;

import java.io.IOException;

import static org.dialectic.jsonapi.Meta.ofEntry;
import static org.dialectic.jsonapi.testsupport.TestSupport.*;

public class JsonapiTest {
    @Test
    public void deserializeBackAgain() throws IOException {
        Jsonapi jsonapi = new Jsonapi("1.2", ofEntry("a", "b"));

        String jsonString = toJsonString(jsonapi);

        Jsonapi deserialized = objectMapper.readValue(jsonString, Jsonapi.class);

        assertJsonEquals(jsonString, toJsonString(deserialized));
    }
}