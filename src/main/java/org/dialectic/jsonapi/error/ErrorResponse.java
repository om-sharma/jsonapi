package org.dialectic.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import org.dialectic.jsonapi.JsonApiResponse;
import org.dialectic.jsonapi.Meta;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public class ErrorResponse<T extends Error> implements JsonApiResponse {
    @JsonProperty
    private List<T> errors;

    @JsonUnwrapped
    private Meta meta;

    public ErrorResponse(List<T> errors) {
        this.errors = errors;
    }

    public ErrorResponse(T... errors) {
        this(Arrays.asList(errors));
    }

    public ErrorResponse<T> withMeta(Meta meta) {
        this.meta = meta;
        return this;
    }
}

