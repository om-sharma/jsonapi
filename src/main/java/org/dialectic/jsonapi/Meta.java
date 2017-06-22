package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

import static org.dialectic.jsonapi.JsonApiObjectMapper.objectMapper;

@EqualsAndHashCode
public class Meta {
    private Map<String, Object> contents = new HashMap<>();

    public static Meta ofEntry(String key, Object value) {
        Meta meta = new Meta();
        meta.contents.put(key, value);
        return meta;
    }

    @JsonCreator
    public static Meta of(Map<String, Object> contents) {
        Meta meta = new Meta();
        meta.contents = contents;
        return meta;
    }

    @SneakyThrows
    public static Meta of(Object contents) {
        Meta meta = new Meta();
        meta.contents = objectMapper.readValue(objectMapper.writeValueAsBytes(contents), new TypeReference<Map<String, Object>>() {
        });
        return meta;
    }

    @JsonValue
    private Map<String, Object> getJson() {
        return contents;
    }

    public Object get(String key) {
        return contents.get(key);
    }

    public void merge(Meta otherMeta) {
        contents.putAll(otherMeta.getJson());
    }
}
