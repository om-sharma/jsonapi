package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.dialectic.jsonapi.error.Error;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse<T extends Error> implements JsonApiResponse {
    private List<T> errors;

    public ErrorResponse(List<T> errors) {
        this.errors = errors;
    }

    public ErrorResponse(T... errors) {
        this(Arrays.asList(errors));
    }

    public List<T> getErrors() {
        return errors;
    }
}
