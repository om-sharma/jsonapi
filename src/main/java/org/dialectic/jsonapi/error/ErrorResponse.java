package org.dialectic.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dialectic.jsonapi.JsonApiResponse;
import lombok.Getter;

import java.lang.*;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public class ErrorResponse<T extends Error> implements JsonApiResponse {
    @JsonProperty
    private List<T> errors;

    @JsonProperty
    private Object meta;

    @JsonCreator
    public ErrorResponse(@JsonProperty("errors") List<T> errors, @JsonProperty("meta") Object meta) {
        this.errors = errors;
        this.meta = meta;
    }

    public ErrorResponse(List<T> errors) {
        this.errors = errors;
    }

    public ErrorResponse(T... errors) {
        this(Arrays.asList(errors));
    }

    public ErrorResponse<T> withMeta(Object meta) {
        this.meta = meta;
        return this;
    }
}

