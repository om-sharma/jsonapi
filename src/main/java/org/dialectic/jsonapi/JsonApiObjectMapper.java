package org.dialectic.jsonapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.SneakyThrows;

public class JsonApiObjectMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setFilterProvider(new SimpleFilterProvider().setDefaultFilter(SimpleBeanPropertyFilter.serializeAll()));
    }

    @SneakyThrows
    public static <T> T readToClass(Object object, Class<T> classType) {
        return objectMapper.readValue(objectMapper.writeValueAsBytes(object), classType);
    }
}
