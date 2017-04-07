package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Data<T> {
    private T attributes;
    private String id;
    private String type;

    public static <T> Data<T> with(String id, String type, T attributes){
        Data<T> data = new Data<>();
        data.id = id;
        data.type = type;
        data.attributes = attributes;
        return data;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public T getAttributes() {
        return attributes;
    }
}
